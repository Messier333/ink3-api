package shop.ink3.api.book.book.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(BookController.class)
class BookControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private BookService bookService;
//
//    private BookDetailResponse bookDetailResponse;
//    private BookPreviewResponse bookPreviewResponse;
//
//    @BeforeEach
//    void setUp() {
//        CategoryTreeDto category = new CategoryTreeDto(
//                3L, "한국소설", 1L, List.of()
//        );
//        List<CategoryTreeDto> categories = List.of(category);
//
//        AuthorDto author = new AuthorDto(
//                100L, "홍길동", "저자"
//        );
//        List<AuthorDto> authors = List.of(author);
//
//        TagResponse tag = new TagResponse(
//                50L, "베스트셀러"
//        );
//        List<TagResponse> tags = List.of(tag);
//
//        this.bookDetailResponse = new BookDetailResponse(
//                1L,                             // id
//                "1234567890123",                // isbn
//                "책 제목",                        // title
//                "책 내용 요약",                   // contents
//                "상세 설명",                      // description
//                "출판사",                          // publisherName
//                LocalDate.of(2024, 1, 1),       // publishedAt
//                20000,                          // originalPrice
//                18000,                          // salePrice
//                10,                             // discountRate
//                100,                            // quantity
//                BookStatus.AVAILABLE,           // status
//                true,                           // isPackable
//                "https://example.com/image.jpg", // thumbnailUrl
//                categories,                     // List<CategoryResponse>
//                authors,                        // List<AuthorDto>
//                tags,                           // List<TagResponse>
//                4.5,                            // ⭐️ averageRating 추가
//                0L
//        );
//
//        this.bookPreviewResponse = new BookPreviewResponse(
//                1L, "책 제목", 20000, 18000, 10,
//                "https://example.com/image.jpg",
//                true,
//                List.of("홍길동 (저자)"), 5, 5
//        );
//    }
//
//    @Test
//    @DisplayName("전체 도서 목록 조회")
//    void getBooks() throws Exception {
//        PageResponse<BookDetailResponse> pageResponse = PageResponse.from(new PageImpl<>(List.of(bookDetailResponse)));
//        when(bookService.getBooks(any())).thenReturn(pageResponse);
//
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("Top5 베스트셀러 조회")
//    void getTop5BestsellerBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getTop5BestSellerBooks()).thenReturn(response);
//
//        mockMvc.perform(get("/books/bestseller"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("Top5 신간 조회")
//    void getTop5NewBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getTop5NewBooks()).thenReturn(response);
//
//        mockMvc.perform(get("/books/new"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("Top5 추천도서 조회")
//    void getTop5RecommendedBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getTop5RecommendedBooks()).thenReturn(response);
//
//        mockMvc.perform(get("/books/recommend"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("전체 베스트셀러 조회")
//    void getAllBestsellerBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getAllBestSellerBooks(eq(SortType.REVIEW), any())).thenReturn(response);
//
//        mockMvc.perform(get("/books/bestseller-all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("전체 신간 조회")
//    void getAllNewBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getAllNewBooks(eq(SortType.REVIEW), any())).thenReturn(response);
//
//        mockMvc.perform(get("/books/new-all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
//
//    @Test
//    @DisplayName("전체 추천도서 조회")
//    void getAllRecommendedBooks() throws Exception {
//        PageResponse<BookPreviewResponse> response = PageResponse.from(new PageImpl<>(List.of(bookPreviewResponse)));
//        when(bookService.getAllRecommendedBooks(eq(SortType.REVIEW), any())).thenReturn(response);
//
//        mockMvc.perform(get("/books/recommend-all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.content[0].title").value("책 제목"));
//    }
}
