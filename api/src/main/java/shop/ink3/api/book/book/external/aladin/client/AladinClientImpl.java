package shop.ink3.api.book.book.external.aladin.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shop.ink3.api.book.book.exception.AladinBookNotFoundException;
import shop.ink3.api.book.book.exception.AladinParsingException;
import shop.ink3.api.book.book.external.aladin.dto.AladinBookResponse;
import shop.ink3.api.common.dto.PageResponse;

@Component
@RequiredArgsConstructor
public class AladinClientImpl implements AladinClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${aladin.ttb-key}")
    private String ttbKey;
    private static final String LOOKUP_URL = "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx";
    private static final String SEARCH_URL = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx";

    @Override
    public AladinBookResponse fetchBookByIsbn(String isbn13) {
        String url = LOOKUP_URL +
                "?ttbkey=" + ttbKey +
                "&itemIdType=ISBN13" +
                "&ItemId=" + isbn13 +
                "&output=JS" +
                "&Version=20131101" +
                "&OptResult=toc,fulldescription";

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("item");

            if (!items.isArray() || items.size() == 0) {
                throw new AladinBookNotFoundException(isbn13);
            }

            JsonNode item = items.get(0);

            return new AladinBookResponse(
                    item.path("title").asText(),
                    item.path("description").asText(),
                    item.path("author").asText(),
                    item.path("publisher").asText(),
                    item.path("pubDate").asText(),
                    item.path("isbn13").asText(),
                    item.path("priceStandard").asInt(),
                    item.path("cover").asText(),
                    item.path("categoryName").asText()
            );
        } catch (AladinBookNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AladinParsingException(e);
        }
    }

    @Override
    public PageResponse<AladinBookResponse> fetchBookByKeyword(String keyword, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int start = pageNumber + 1;

        String url = SEARCH_URL +
                "?ttbkey=" + ttbKey +
                "&Query=" + keyword +
                "&QueryType=Keyword" +
                "&MaxResults=" + pageSize +
                "&start=" + start +
                "&SearchTarget=Book" +
                "&output=JS" +
                "&Version=20131101";

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("item");

            List<AladinBookResponse> books = new ArrayList<>();
            for (JsonNode item : items) {
                books.add(new AladinBookResponse(
                        item.path("title").asText(),
                        item.path("description").asText(),
                        item.path("author").asText(),
                        item.path("publisher").asText(),
                        item.path("pubDate").asText(),
                        item.path("isbn13").asText(),
                        item.path("priceStandard").asInt(),
                        item.path("cover").asText().replace("coversum", "cover500"),
                        item.path("categoryName").asText()
                ));
            }
            // 알라딘 api에서 가져오는 도서는 200개 제한
            int total = Math.min(root.path("totalResults").asInt(200), 200);
            Page<AladinBookResponse> page = new PageImpl<>(books, PageRequest.of(pageNumber, pageSize), total);

            return PageResponse.from(page);

        } catch (Exception e) {
            throw new AladinParsingException(e);
        }
    }
}
