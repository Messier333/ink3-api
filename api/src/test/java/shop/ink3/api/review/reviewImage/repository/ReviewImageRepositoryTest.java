package shop.ink3.api.review.reviewImage.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ReviewImageRepositoryTest {
//
//    @Autowired
//    private ReviewImageRepository reviewImageRepository;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    private Review review;
//
//    @BeforeEach
//    void setUp() {
//        Publisher publisher = Publisher.builder()
//                .name("출판사")
//                .build();
//        em.persist(publisher);
//
//        Book book = Book.builder()
//                .isbn("1234567890123")
//                .title("책 제목")
//                .contents("요약")
//                .description("설명")
//                .publishedAt(LocalDate.of(2024, 1, 1))
//                .originalPrice(20000)
//                .salePrice(18000)
//                .quantity(100)
//                .status(BookStatus.AVAILABLE)
//                .isPackable(true)
//                .thumbnailUrl("url")
//                .publisher(publisher)
//                .build();
//        em.persist(book);
//
//        Membership membership = Membership.builder()
//                .name("기본멤버십")
//                .conditionAmount(10000)
//                .pointRate(5)
//                .isActive(true)
//                .isDefault(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//        em.persist(membership);
//
//        User user = User.builder()
//                .loginId("user1")
//                .password("pw")
//                .name("홍길동")
//                .email("user1@example.com")
//                .phone("010-0000-0000")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .status(UserStatus.ACTIVE)
//                .point(1000)
//                .lastLoginAt(LocalDateTime.now())
//                .createdAt(LocalDateTime.now())
//                .membership(membership)
//                .build();
//        em.persist(user);
//
//        OrderBook orderBook = OrderBook.builder()
//                .book(book)
//                .price(20000)
//                .quantity(1)
//                .build();
//        em.persist(orderBook);
//
//        review = new Review(user, orderBook, "제목", "내용", 5);
//        em.persist(review);
//
//        ReviewImage image1 = ReviewImage.builder().review(review).imageUrl("img1.jpg").build();
//        ReviewImage image2 = ReviewImage.builder().review(review).imageUrl("img2.jpg").build();
//        em.persist(image1);
//        em.persist(image2);
//
//        em.flush();
//        em.clear();
//    }
//
//    @Test
//    @DisplayName("리뷰 ID로 이미지 조회")
//    void findByReviewId() {
//        List<ReviewImage> images = reviewImageRepository.findByReviewId(review.getId());
//
//        assertThat(images).hasSize(2);
//        assertThat(images).extracting("imageUrl")
//                .containsExactlyInAnyOrder("img1.jpg", "img2.jpg");
//    }
//
//    @Test
//    @DisplayName("리뷰 ID 목록으로 이미지 일괄 조회")
//    void findByReviewIdIn() {
//        List<ReviewImage> images = reviewImageRepository.findByReviewIdIn(List.of(review.getId()));
//
//        assertThat(images).hasSize(2);
//        assertThat(images).extracting("imageUrl")
//                .containsExactlyInAnyOrder("img1.jpg", "img2.jpg");
//    }
}
