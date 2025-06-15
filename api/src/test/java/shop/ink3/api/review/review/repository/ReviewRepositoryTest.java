package shop.ink3.api.review.review.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    private User user;
//    private Book book;
//    private OrderBook orderBook;
//
//    @BeforeEach
//    void setUp() {
//        Publisher publisher = Publisher.builder().name("출판사1").build();
//        em.persist(publisher);
//
//        book = Book.builder()
//                .isbn("1234567890123")
//                .title("책 제목")
//                .contents("내용 요약")
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
//                .name("멤버쉽1")
//                .conditionAmount(10000)
//                .pointRate(5)
//                .isActive(true)
//                .isDefault(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//        em.persist(membership);
//
//        user = User.builder()
//                .loginId("test")
//                .password("test")
//                .name("홍길동")
//                .email("test@test.com")
//                .phone("010-1234-5678")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .status(UserStatus.ACTIVE)
//                .point(1000)
//                .lastLoginAt(LocalDateTime.now())
//                .createdAt(LocalDateTime.now())
//                .membership(membership)
//                .build();
//        em.persist(user);
//
//        orderBook = OrderBook.builder()
//                .book(book)
//                .price(20000)
//                .quantity(1)
//                .build();
//        em.persist(orderBook);
//
//        Review review = new Review(user, orderBook, "좋아요", "추천합니다", 5);
//        em.persist(review);
//        em.flush();
//        em.clear();
//    }
//
//    @Test
//    @DisplayName("도서에 대한 리뷰 목록 조회")
//    void findListByBookId() {
//        Page<ReviewDefaultListResponse> page = reviewRepository.findListByBookId(
//                PageRequest.of(0, 10), book.getId()
//        );
//        assertThat(page.getTotalElements()).isEqualTo(1);
//        assertThat(page.getContent().getFirst().rating()).isEqualTo(5);
//        assertThat(page.getContent().getFirst().title()).isEqualTo("좋아요");
//    }
//
//    @Test
//    @DisplayName("사용자에 대한 리뷰 목록 조회")
//    void findListByUserId() {
//        Page<ReviewDefaultListResponse> page = reviewRepository.findListByUserId(
//                PageRequest.of(0, 10), user.getId()
//        );
//        assertThat(page.getTotalElements()).isEqualTo(1);
//        assertThat(page.getContent().getFirst().rating()).isEqualTo(5);
//        assertThat(page.getContent().getFirst().title()).isEqualTo("좋아요");
//    }
//
//    @Test
//    @DisplayName("orderBookId로 리뷰 존재 여부 확인")
//    void existsByOrderBookId() {
//        boolean exists = reviewRepository.existsByOrderBookId(orderBook.getId());
//        assertThat(exists).isTrue();
//    }
}
