package shop.ink3.api.book.book.service;

class BookServiceTest {
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private LikeRepository likeRepository;
//
//    @InjectMocks
//    private BookService bookService;
//
//    private Book book;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        book = Book.builder()
//                .id(1L)
//                .isbn("1234567890123")
//                .title("책 제목")
//                .contents("내용")
//                .description("설명")
//                .publishedAt(LocalDate.of(2024, 1, 1))
//                .originalPrice(20000)
//                .salePrice(18000)
//                .discountRate(10)
//                .status(BookStatus.AVAILABLE)
//                .isPackable(true)
//                .thumbnailUrl("https://example.com/image.jpg")
//                .quantity(100)
//                .build();
//    }
//
//    @Test
//    @DisplayName("도서 단건 조회 성공")
//    void getBookSuccess() {
//        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
//        when(reviewRepository.findAverageRatingByBookId(1L)).thenReturn(Optional.of(4.5));
//
//        BookDetailResponse result = bookService.getBook(1L);
//        assertThat(result.id()).isEqualTo(1L);
//        assertThat(result.title()).isEqualTo("책 제목");
//        assertThat(result.averageRating()).isEqualTo(4.5);
//    }
//
//    @Test
//    @DisplayName("전체 도서 조회 성공")
//    void getBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);
//        PageResponse<BookDetailResponse> response = bookService.getBooks(PageRequest.of(0, 10));
//        assertThat(response.content()).hasSize(1);
//        assertThat(response.content().get(0).title()).isEqualTo("책 제목");
//    }
//
//    @Test
//    @DisplayName("Top5 베스트셀러 조회 성공")
//    void getTop5BestSellerBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findBestSellerBooks(any(Pageable.class))).thenReturn(page);
//        PageResponse<BookPreviewResponse> result = bookService.getTop5BestSellerBooks();
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//    }
//
//    @Test
//    @DisplayName("Top5 추천도서 조회 성공")
//    void getTop5RecommendedBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findRecommendedBooks(any(Pageable.class))).thenReturn(page);
//        PageResponse<BookPreviewResponse> result = bookService.getTop5RecommendedBooks();
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//    }
//
//    @Test
//    @DisplayName("Top5 신간 도서 조회 성공")
//    void getTop5NewBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findAllByOrderByPublishedAtDesc(any(Pageable.class))).thenReturn(page);
//        PageResponse<BookPreviewResponse> result = bookService.getTop5NewBooks();
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//    }
//
//    @Test
//    @DisplayName("전체 베스트셀러 조회 성공")
//    void getAllBestSellerBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findSortedBestSellerBooks(eq(SortType.REVIEW), any(Pageable.class))).thenReturn(page);
//        when(reviewRepository.countByOrderBookBookId(book.getId())).thenReturn(5L);
//        when(likeRepository.countByBookId(book.getId())).thenReturn(3L);
//
//        PageResponse<BookPreviewResponse> result = bookService.getAllBestSellerBooks(SortType.REVIEW,
//                PageRequest.of(0, 5));
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//        assertThat(result.content().get(0).reviewCount()).isEqualTo(5L);
//        assertThat(result.content().get(0).likeCount()).isEqualTo(3L);
//    }
//
//    @Test
//    @DisplayName("전체 신간 도서 조회 성공")
//    void getAllNewBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findSortedNewBooks(eq(SortType.REVIEW), any(Pageable.class))).thenReturn(page);
//        when(reviewRepository.countByOrderBookBookId(book.getId())).thenReturn(5L);
//        when(likeRepository.countByBookId(book.getId())).thenReturn(3L);
//
//        PageResponse<BookPreviewResponse> result = bookService.getAllNewBooks(SortType.REVIEW, PageRequest.of(0, 5));
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//        assertThat(result.content().get(0).reviewCount()).isEqualTo(5L);
//        assertThat(result.content().get(0).likeCount()).isEqualTo(3L);
//    }
//
//    @Test
//    @DisplayName("전체 추천 도서 조회 성공")
//    void getAllRecommendedBooksSuccess() {
//        Page<Book> page = new PageImpl<>(List.of(book));
//        when(bookRepository.findSortedRecommendedBooks(eq(SortType.REVIEW), any(Pageable.class))).thenReturn(page);
//        when(reviewRepository.countByOrderBookBookId(book.getId())).thenReturn(5L);
//        when(likeRepository.countByBookId(book.getId())).thenReturn(3L);
//
//        PageResponse<BookPreviewResponse> result = bookService.getAllRecommendedBooks(SortType.REVIEW,
//                PageRequest.of(0, 5));
//        assertThat(result.content()).hasSize(1);
//        assertThat(result.content().get(0).title()).isEqualTo("책 제목");
//        assertThat(result.content().get(0).reviewCount()).isEqualTo(5L);
//        assertThat(result.content().get(0).likeCount()).isEqualTo(3L);
//    }
}
