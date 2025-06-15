package shop.ink3.api.review.review.service;

class ReviewServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private OrderBookRepository orderBookRepository;
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private ReviewImageRepository reviewImageRepository;
//
//    @Mock
//    private PointService pointService;
//
//    @Mock
//    private PointPolicyService pointPolicyService;
//
//    @Mock
//    private MinioService minioService;
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    private User user;
//    private Order order;
//    private OrderBook orderBook;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user = User.builder().id(1L).name("user1").build();
//        order = Order.builder().id(1L).user(user).build();
//        orderBook = OrderBook.builder()
//                .id(1L)
//                .book(Book.builder().id(1L).build())
//                .order(order)
//                .build();
//
//        when(pointPolicyService.getPointPolicy(anyLong())).thenReturn(
//                new PointPolicyResponse(
//                        1L,
//                        "기본정책",
//                        100,
//                        200,
//                        300,
//                        5,
//                        true,
//                        LocalDateTime.now()
//                )
//        );
//
//        when(pointService.earnPoint(anyLong(), any())).thenReturn(
//                PointHistory.builder()
//                        .id(1L)
//                        .user(user)
//                        .delta(100)
//                        .status(PointHistoryStatus.EARN)
//                        .description("100포인트가 적립되었습니다.")
//                        .createdAt(LocalDateTime.now())
//                        .build()
//        );
//
//        ReflectionTestUtils.setField(reviewService, "bucket", "test-bucket");
//    }
//
//
//    @Test
//    @DisplayName("리뷰 등록")
//    void addReview() {
//        ReviewRequest request = new ReviewRequest(1L, 1L, "제목", "내용", 5);
//        Review review = new Review(user, orderBook, "제목", "내용", 5);
//        ReflectionTestUtils.setField(review, "id", 1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(orderBookRepository.findById(1L)).thenReturn(Optional.of(orderBook));
//        when(reviewRepository.existsByOrderBookId(1L)).thenReturn(false);
//        when(reviewRepository.save(any())).thenReturn(review);
//        when(reviewImageRepository.findByReviewId(1L)).thenReturn(List.of());
//
//        ReviewResponse response = reviewService.addReview(request, List.of());
//
//        assertThat(orderBook.getOrder()).isNotNull();
//        assertThat(orderBook.getOrder().getUser()).isEqualTo(user);
//        assertThat(response.title()).isEqualTo("제목");
//        assertThat(response.images()).isEmpty();
//    }
//
//    @Test
//    @DisplayName("리뷰 수정")
//    void updateReview() {
//        Review review = new Review(user, orderBook, "제목", "내용", 3);
//        ReflectionTestUtils.setField(review, "id", 1L);
//
//        ReviewUpdateRequest request = new ReviewUpdateRequest("제목 수정", "내용 수정", 4);
//
//        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
//        when(reviewImageRepository.findByReviewId(1L)).thenReturn(List.of());
//        when(minioUploader.upload(any(MultipartFile.class), anyString())).thenReturn("new-image.jpg");
//
//        ReviewResponse response = reviewService.updateReview(1L, request, List.of(mock(MultipartFile.class)), 1L);
//
//        assertThat(response.title()).isEqualTo("제목 수정");
//        assertThat(response.rating()).isEqualTo(4);
//    }
//
//    @Test
//    @DisplayName("도서 ID로 리뷰 목록 조회")
//    void getReviewsByBookId() {
//        ReviewDefaultListResponse dto = new ReviewDefaultListResponse(1L, 1L, 1L, 1L, "user1", "제목", "내용", 5, null,
//                null);
//        Page<ReviewDefaultListResponse> page = new PageImpl<>(List.of(dto));
//
//        Review review = new Review(user, orderBook, "제목", "내용", 5);
//        ReflectionTestUtils.setField(review, "id", 1L);
//
//        ReviewImage image = ReviewImage.builder()
//                .imageUrl("img1.jpg")
//                .review(review)
//                .build();
//
//        when(reviewRepository.findListByBookId(any(), anyLong())).thenReturn(page);
//        when(reviewImageRepository.findByReviewIdIn(List.of(1L))).thenReturn(List.of(image));
//        when(minioUploader.getPresignedUrl(anyString(), anyString()))
//                .thenReturn("http://storage.java21.net:8000/ink3-dev-reviews-images/sample.jpg");
//        when(presignUrlPrefixUtil.addPrefixUrl(anyString()))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        PageResponse<ReviewListResponse> response = reviewService.getReviewsByBookId(PageRequest.of(0, 10), 1L);
//
//        assertThat(response.content()).hasSize(1);
//        assertThat(response.content().getFirst().images()).hasSize(1);
//    }
//
//    @Test
//    @DisplayName("유저의 리뷰 목록 조회")
//    void getReviewsByUserId() {
//        ReviewDefaultListResponse dto = new ReviewDefaultListResponse(
//                1L, 1L, 1L, 1L, "user1", "제목", "내용", 5, null, null
//        );
//        Page<ReviewDefaultListResponse> page = new PageImpl<>(List.of(dto));
//
//        Review review = new Review(user, orderBook, "제목", "내용", 5);
//        ReflectionTestUtils.setField(review, "id", 1L);
//
//        ReviewImage image = ReviewImage.builder()
//                .imageUrl("img1.jpg")
//                .review(review)
//                .build();
//
//        when(reviewRepository.findListByUserId(any(), anyLong())).thenReturn(page);
//        when(reviewImageRepository.findByReviewIdIn(List.of(1L))).thenReturn(List.of(image));
//        when(minioUploader.getPresignedUrl(anyString(), anyString()))
//                .thenReturn("http://storage.java21.net:8000/ink3-dev-reviews-images/sample.jpg");
//        when(presignUrlPrefixUtil.addPrefixUrl(anyString()))
//                .thenAnswer(invocation -> invocation.getArgument(0));
//
//        PageResponse<ReviewListResponse> response = reviewService.getReviewsByUserId(PageRequest.of(0, 10), 1L);
//
//        assertThat(response.content()).hasSize(1);
//        assertThat(response.content().get(0).images()).hasSize(1);
//    }
//
//    @Test
//    @DisplayName("리뷰 수정 - 예외")
//    void updateReviewFail() {
//        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ReviewNotFoundException.class,
//                () -> reviewService.updateReview(1L, new ReviewUpdateRequest("제목", "내용", 3), null, 1L));
//    }
//
//    @Test
//    @DisplayName("리뷰 삭제")
//    void deleteReview() {
//        Review review = new Review(user, orderBook, "제목", "내용", 5);
//        ReflectionTestUtils.setField(review, "id", 1L);
//
//        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
//        when(reviewImageRepository.findByReviewId(1L)).thenReturn(List.of());
//
//        reviewService.deleteReview(1L);
//
//        verify(reviewRepository).deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("리뷰 삭제 - 예외")
//    void deleteReviewFail() {
//        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReview(1L));
//    }
}
