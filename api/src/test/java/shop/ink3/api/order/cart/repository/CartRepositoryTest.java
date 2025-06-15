package shop.ink3.api.order.cart.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CartRepositoryTest {
//    @Autowired
//    private CartRepository cartRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private User user;
//    private Book book;
//
//    @BeforeEach
//    void setUp() {
//        Membership membership = Membership.builder()
//                .name("membership1")
//                .conditionAmount(10000)
//                .pointRate(5)
//                .isActive(true)
//                .isDefault(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//        entityManager.persist(membership);
//
//        user = User.builder()
//                .loginId("test")
//                .password("test")
//                .name("test")
//                .email("test@test.com")
//                .phone("010-1234-5678")
//                .birthday(LocalDate.of(2025, 1, 1))
//                .point(1000)
//                .status(UserStatus.ACTIVE)
//                .lastLoginAt(LocalDateTime.now())
//                .createdAt(LocalDateTime.now())
//                .membership(membership)
//                .build();
//        entityManager.persist(user);
//
//        Publisher publisher = Publisher.builder()
//                .name("출판사1")
//                .build();
//        entityManager.persist(publisher);
//
//        book = Book.builder()
//                .isbn("1234567890123")
//                .title("예제 책 제목")
//                .contents("책 내용 요약")
//                .description("책 상세 설명")
//                .publishedAt(LocalDate.of(2024, 1, 1))
//                .originalPrice(20000)
//                .salePrice(18000)
//                .quantity(100)
//                .status(BookStatus.AVAILABLE)
//                .isPackable(true)
//                .thumbnailUrl("https://example.com/image.jpg")
//                .publisher(publisher)
//                .build();
//        entityManager.persist(book);
//    }
//
//    @Test
//    @DisplayName("회원 ID로 장바구니 조회")
//    void findByUserId_success() {
//        Cart cart = Cart.builder()
//                .user(user)
//                .book(book)
//                .quantity(100)
//                .build();
//
//        cartRepository.save(cart);
//
//        Optional<Cart> found = cartRepository.findById(cart.getId());
//
//        assertThat(found).isPresent();
//        assertThat(found.get().getId()).isEqualTo(1L);
//    }
}
