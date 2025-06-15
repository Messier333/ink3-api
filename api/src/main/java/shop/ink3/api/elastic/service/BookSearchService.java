package shop.ink3.api.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.ink3.api.common.config.ElasticsearchConfig;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.elastic.model.BookDocument;
import shop.ink3.api.elastic.model.BookSortOption;
import shop.ink3.api.elastic.repository.BookSearchRedisRepository;

@ConditionalOnBean(ElasticsearchConfig.class)
@Slf4j
@RequiredArgsConstructor
@Service
public class BookSearchService {
    @Value("${elasticsearch.index}")
    private String index;

    private final ElasticsearchClient client;
    private final BookSearchRedisRepository bookSearchRedisRepository;

    public void indexBook(BookDocument bookDocument) {
        try {
            client.index(i -> i
                    .index(index)
                    .id(bookDocument.getId().toString())
                    .document(bookDocument)
            );
        } catch (IOException e) {
            log.error("Elasticsearch 색인 실패: {}", e.getMessage(), e);
        }
    }

    public BookDocument getBook(long bookId) {
        try {
            GetResponse<BookDocument> response = client.get(
                    g -> g.index(index).id(String.valueOf(bookId)),
                    BookDocument.class
            );
            return response.found() ? response.source() : null;
        } catch (IOException e) {
            return null;
        }
    }

    public PageResponse<BookDocument> searchBooksByKeyword(
            String keyword,
            int page,
            int size,
            BookSortOption sortOption
    ) throws IOException {
        BookSortOption safeSortOption = sortOption == null ? BookSortOption.POPULARITY : sortOption;

        SearchResponse<BookDocument> response = client.search(search -> search
                        .index(index)
                        .from(page * size)
                        .size(size)
                        .query(q -> q.
                                bool(b -> {
                                            b.should(sh -> sh.match(m -> m.field("title").query(keyword).boost(100f)));
                                            b.should(sh -> sh.match(m -> m.field("description").query(keyword).boost(10f)));
                                            b.should(sh -> sh.match(m -> m.field("tags").query(keyword).boost(50f)));
                                            b.should(sh -> sh.match(m -> m.field("authors").query(keyword).boost(50f)));

                                            if (safeSortOption == BookSortOption.RATING) {
                                                b.filter(f -> f.range(r -> r.number(n -> n
                                                        .field("reviewCount")
                                                        .gte(100.0)
                                                )));
                                            }

                                            return b;
                                        }
                                )
                        )
                        .sort(s -> s.field(f -> f.field(safeSortOption.getSortField()).order(safeSortOption.getSortOrder()))),
                BookDocument.class
        );

        return PageResponse.from(wrapToPage(response, page, size));
    }

    public PageResponse<BookDocument> searchBooksByCategory(
            String category,
            int page,
            int size,
            BookSortOption sortOption
    ) throws IOException {
        BookSortOption safeSortOption = sortOption == null ? BookSortOption.POPULARITY : sortOption;

        SearchResponse<BookDocument> response = client.search(search -> search
                        .index(index)
                        .from(page * size)
                        .size(size)
                        .query(q -> q
                                .bool(b -> b.filter(f -> f.term(t -> t.field("categories").value(category))))
                        )
                        .sort(s -> s.field(f -> f.field(safeSortOption.getSortField()).order(safeSortOption.getSortOrder()))),
                BookDocument.class
        );
        return PageResponse.from(wrapToPage(response, page, size));
    }

    public void updateBook(BookDocument bookDocument) {
        try {
            client.update(u -> u
                            .index(index)
                            .id(bookDocument.getId().toString())
                            .upsert(bookDocument),
                    BookDocument.class
            );
        } catch (IOException e) {
            log.error("Elasticsearch 업데이트 실패: {}", e.getMessage(), e);
        }
    }

    public void updateViewAndSearchCount() {
        Map<Object, Object> viewCounts = bookSearchRedisRepository.getAllViewCounts();
        Map<Object, Object> searchCounts = bookSearchRedisRepository.getAllSearchCounts();
        bookSearchRedisRepository.clearAllCounts();

        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(viewCounts.keySet().stream().map(Object::toString).toList());
        allKeys.addAll(searchCounts.keySet().stream().map(Object::toString).toList());

        for (String key : allKeys) {
            long bookId = Long.parseLong(key);
            BookDocument bookDocument = getBook(bookId);
            if (bookDocument == null) {
                continue;
            }

            if (viewCounts.containsKey(key)) {
                int viewCount = Integer.parseInt(viewCounts.get(key).toString());
                bookDocument.updateViewCount(viewCount);
            }

            if (searchCounts.containsKey(key)) {
                int searchCount = Integer.parseInt(searchCounts.get(key).toString());
                bookDocument.updateSearchCount(searchCount);
            }

            indexBook(bookDocument);
        }
    }

    public void deleteBook(long bookId) {
        try {
            client.delete(d -> d.index(index).id(String.valueOf(bookId)));
        } catch (IOException e) {
            log.error("Elasticsearch 삭제 실패: {}", e.getMessage(), e);
        }
    }

    private <T> PageImpl<T> wrapToPage(SearchResponse<T> response, int page, int size) {
        List<T> content = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .toList();

        long total = response.hits().total() != null ? response.hits().total().value() : content.size();

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(content, pageable, total);
    }
}
