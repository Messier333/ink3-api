package shop.ink3.api.book.book.external.aladin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.ink3.api.book.book.dto.BookDetailResponse;
import shop.ink3.api.book.book.dto.BookRegisterRequest;
import shop.ink3.api.book.book.external.aladin.client.AladinClient;
import shop.ink3.api.book.book.external.aladin.dto.AladinBookResponse;
import shop.ink3.api.book.book.service.BookService;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.common.dto.PageResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aladin")
public class AladinController {

    private final BookService bookService;
    private final AladinClient aladinClient;

    // Keyword로 알라딘 API의 도서 리스트 조회, /aladin?keyword=도서
    @GetMapping
    public ResponseEntity<CommonResponse<PageResponse<AladinBookResponse>>> getBooksByKeyword(
            @RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(CommonResponse.success(aladinClient.fetchBookByKeyword(keyword, pageable)));
    }

    // 알라딘 API에서 Keyword로 조회한 도서 리스트에서 하나의 도서를 선택하고 자체적으로 설정할 내용 입력하여 도서 등록
    @PostMapping("/book-register")
    public ResponseEntity<CommonResponse<BookDetailResponse>> registerBook(
            @RequestBody @Valid BookRegisterRequest request) {
        return ResponseEntity.ok(CommonResponse.success(bookService.registerBookByAladin(request)));
    }
}
