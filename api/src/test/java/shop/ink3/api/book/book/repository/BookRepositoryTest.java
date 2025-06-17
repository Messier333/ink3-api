package shop.ink3.api.book.book.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private Publisher publisher;
//
//    @BeforeEach
//    void setUp() {
//        publisher = Publisher.builder().name("출판사").build();
//        entityManager.persist(publisher);
//
//        for (int i = 1; i <= 5; i++) {
//            Book book = Book.builder()
//                .title("도서 " + i)
//                .isbn("isbn" + i)
//                .description("설명")
//                .contents("내용")
//                .originalPrice(10000)
//                .salePrice(9000)
//                .quantity(10)
//                .publishedAt(LocalDate.now().minusDays(i))
//                .status(BookStatus.AVAILABLE)
//                .isPackable(true)
//                .thumbnailUrl("https://example.com/image.jpg")
//                .publisher(publisher)
//                .build();
//            entityManager.persist(book);
//        }
//    }
//
//    @Test
//    @DisplayName("베스트셀러 도서 조회")
//    void findBestSellerBooks_success() {
//        Book book = Book.builder()
//            .title("베스트셀러 도서")
//            .isbn("isbn-best")
//            .description("설명")
//            .contents("내용")
//            .originalPrice(15000)
//            .salePrice(12000)
//            .quantity(100)
//            .publishedAt(LocalDate.now())
//            .status(BookStatus.AVAILABLE)
//            .isPackable(true)
//            .thumbnailUrl("https://example.com/image.jpg")
//            .publisher(publisher)
//            .build();
//        entityManager.persist(book);
//
//        entityManager.createNativeQuery("""
//                INSERT INTO orders (id, status, ordered_at, orderer_name, orderer_phone)
//                VALUES (1, 'DELIVERED', now(), '홍길동', '010-1234-5678')
//            """).executeUpdate();
//        entityManager.createNativeQuery("""
//                INSERT INTO order_books (order_id, book_id, price, quantity)
//                VALUES (1, {bookId}, 12000, 5)
//            """.replace("{bookId}", String.valueOf(book.getId()))).executeUpdate();
//
//        Page<Book> result = bookRepository.findBestSellerBooks(PageRequest.of(0, 5));
//        assertThat(result.getContent()).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("최근 출간순 정렬 조회")
//    void findAllByOrderByPublishedAtDesc_success() {
//        Page<Book> result = bookRepository.findAllByOrderByPublishedAtDesc(PageRequest.of(0, 5));
//        assertThat(result.getContent()).hasSize(5);
//        assertThat(result.getContent().getFirst().getTitle()).isEqualTo("도서 1");
//    }
//
//    @Test
//    @DisplayName("추천 도서 조회")
//    void findRecommendedBooks_success() {
//        Membership membership = Membership.builder()
//            .name("기본 회원권")
//            .conditionAmount(0)
//            .pointRate(5)
//            .isActive(true)
//            .isDefault(true)
//            .createdAt(LocalDateTime.now())
//            .build();
//        entityManager.persist(membership);
//
//        User user = User.builder()
//            .loginId("test")
//            .password("test1234")
//            .name("테스터")
//            .email("test@example.com")
//            .phone("010-1111-2222")
//            .birthday(LocalDate.of(1995, 1, 1))
//            .point(0)
//            .status(UserStatus.ACTIVE)
//            .lastLoginAt(LocalDateTime.now())
//            .createdAt(LocalDateTime.now())
//            .membership(membership)
//            .build();
//        entityManager.persist(user);
//
//        Book book = Book.builder()
//            .title("추천 도서")
//            .isbn("isbn-reco")
//            .description("설명")
//            .contents("내용")
//            .originalPrice(15000)
//            .salePrice(12000)
//            .quantity(100)
//            .publishedAt(LocalDate.now())
//            .status(BookStatus.AVAILABLE)
//            .isPackable(true)
//            .thumbnailUrl("https://example.com/image.jpg")
//            .publisher(publisher)
//            .build();
//        entityManager.persist(book);
//
//        Like like = shop.ink3.api.user.like.entity.Like.builder()
//            .user(user)
//            .book(book)
//            .build();
//        entityManager.persist(like);
//
//        Page<Book> result = bookRepository.findRecommendedBooks(PageRequest.of(0, 5));
//        assertThat(result.getContent()).isNotEmpty();
//    }
}
