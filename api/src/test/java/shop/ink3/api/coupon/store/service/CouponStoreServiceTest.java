//package shop.ink3.api.coupon.store.service;
//
//import java.util.List;
//import org.junit.jupiter.api.extension.ExtendWith;
//import shop.ink3.api.coupon.store.dto.CouponStoreDto;
//import shop.ink3.api.coupon.store.entity.CouponStatus;
//import shop.ink3.api.coupon.store.entity.OriginType;
//
//@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
//class CouponStoreServiceTest {
//
//    @Mock private CouponRepository couponRepository;
//    @Mock private UserRepository userRepository;
//    @Mock private CouponStoreRepository couponStoreRepository;
//    @Mock private BookCouponRepository bookCouponRepository;
//    @Mock private CategoryCouponService categoryCouponService;
//    @Mock private BookRepository bookRepository;
//    @Mock private CategoryRepository categoryRepository;
//
//    @InjectMocks private CouponStoreService couponStoreService;
//
//    private User user;
//    private Coupon coupon;
//    private final Long USER_ID = 1L;
//    private final Long BOOK_ID = 10L;
//    private LocalDateTime now;
//
//    @BeforeEach
//    void setUp() {
//        user = mock(User.class);
//        lenient().when(user.getId()).thenReturn(1L);
//
//        coupon = mock(Coupon.class);
//        lenient().when(coupon.getId()).thenReturn(100L);
//        now = LocalDateTime.now();
//    }
//
//    // ===========================
//    // issueCoupon() 테스트
//    // ===========================
//
//    @Test
//    void issueCoupon_withOriginId_succeeds() {
//        Long userId = 1L;
//        Long couponId = 100L;
//        OriginType originType = OriginType.BOOK;
//        Long originId = 200L;
//        var request = new CommonCouponIssueRequest(userId, couponId, originType, originId);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
//        when(couponStoreRepository.existsByUserIdAndCouponIdAndOriginTypeAndOriginId(
//                userId, couponId, originType, originId)).thenReturn(false);
//
//        CouponStore result = couponStoreService.issueCommonCoupon(request);
//
//        assertNotNull(result);
//        assertEquals(originType, result.getOriginType());
//        assertEquals(originId, result.getOriginId());
//        assertEquals(CouponStatus.READY, result.getStatus());
//        verify(couponStoreRepository).save(any(CouponStore.class));
//    }
//
//    @Test
//    void issueCoupon_withOriginId_duplicate_throwsDuplicateCouponException() {
//        Long userId = 1L;
//        Long couponId = 100L;
//        OriginType originType = OriginType.BOOK;
//        Long originId = 200L;
//        var request = new CouponIssueRequest(couponId, originType, originId);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
//        when(couponStoreRepository.existsByUserIdAndCouponIdAndOriginTypeAndOriginId(
//                userId, couponId, originType, originId)).thenReturn(true);
//
//        assertThrows(DuplicateCouponException.class,
//                () -> couponStoreService.issueCoupon(request, 1L));
//        verify(couponStoreRepository, never()).save(any());
//    }
//
//    @Test
//    void issueCoupon_withoutOriginId_succeeds() {
//        Long userId = 1L;
//        Long couponId = 101L;
//        OriginType originType = OriginType.WELCOME;
//        var request = new CommonCouponIssueRequest(userId, couponId, originType, null);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
//        when(couponStoreRepository.existsByUserIdAndOriginType(userId, originType)).thenReturn(false);
//
//        CouponStore result = couponStoreService.issueCommonCoupon(request);
//
//        assertNotNull(result);
//        assertEquals(originType, result.getOriginType());
//        assertNull(result.getOriginId());
//        assertEquals(CouponStatus.READY, result.getStatus());
//        verify(couponStoreRepository).save(any(CouponStore.class));
//    }
//
//    @Test
//    void issueCoupon_withoutOriginId_duplicate_throwsDuplicateCouponException() {
//        Long userId = 1L;
//        Long couponId = 102L;
//        OriginType originType = OriginType.WELCOME;
//        var request = new CommonCouponIssueRequest(userId, couponId, originType, null);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
//        when(couponStoreRepository.existsByUserIdAndOriginType(userId, originType)).thenReturn(true);
//
//        assertThrows(DuplicateCouponException.class,
//                () -> couponStoreService.issueCommonCoupon(request));
//        verify(couponStoreRepository, never()).save(any());
//    }
//
//    @Test
//    void issueCoupon_userNotFound_throwsUserNotFoundException() {
//        Long userId = 9999L;
//        Long couponId = 100L;
//        var request = new CouponIssueRequest(couponId, OriginType.BOOK, 200L);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class,
//                () -> couponStoreService.issueCoupon(request, userId));
//        verify(couponRepository, never()).findById(any());
//        verify(couponStoreRepository, never()).save(any());
//    }
//
//    @Test
//    void issueCoupon_couponNotFound_throwsCouponNotFoundException() {
//        Long userId = 1L;
//        Long couponId = 9999L;
//        var request = new CouponIssueRequest(couponId, OriginType.BOOK, 200L);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());
//
//        assertThrows(CouponNotFoundException.class,
//                () -> couponStoreService.issueCoupon(request, userId));
//        verify(couponStoreRepository, never()).save(any());
//    }
//
//    // ====================================
//    // getStoresByUserId(), getStoresByCouponId(),
//    // getUnusedStoresByUserId() 테스트
//    // ====================================
//
//    @Test
//    void getStoresByUserId_returnsList() {
//        Long userId = 1L;
//        var sampleStore = CouponStore.builder()
//                .id(1L)
//                .user(user)
//                .coupon(coupon)
//                .originType(OriginType.WELCOME)
//                .status(CouponStatus.READY)
//                .issuedAt(LocalDateTime.now())
//                .build();
//        var stores = List.of(sampleStore);
//
//        when(couponStoreRepository.findByUserId(userId)).thenReturn(stores);
//
//        var result = couponStoreService.getStoresByUserId(userId);
//
//        assertEquals(1, result.size());
//        assertEquals(stores, result);
//    }
//
//    @Test
//    void getStoresByCouponId_returnsList() {
//        Long couponId = 100L;
//        var sampleStore = CouponStore.builder()
//                .id(2L)
//                .user(user)
//                .coupon(coupon)
//                .originType(OriginType.BIRTHDAY)
//                .status(CouponStatus.READY)
//                .issuedAt(LocalDateTime.now())
//                .build();
//        var stores = List.of(sampleStore);
//
//        when(couponStoreRepository.findByCouponId(couponId)).thenReturn(stores);
//
//        var result = couponStoreService.getStoresByCouponId(couponId);
//
//        assertEquals(1, result.size());
//        assertEquals(stores, result);
//    }
//
//    @Test
//    void getUnusedStoresByUserId_returnsOnlyReady() {
//        Long userId = 1L;
//        var store1 = CouponStore.builder()
//                .id(3L)
//                .user(user)
//                .coupon(coupon)
//                .originType(OriginType.WELCOME)
//                .status(CouponStatus.READY)
//                .issuedAt(LocalDateTime.now())
//                .build();
//        var stores = List.of(store1);
//
//        when(couponStoreRepository.findByUserIdAndStatus(userId, CouponStatus.READY)).thenReturn(stores);
//
//        var result = couponStoreService.getUnusedStoresByUserId(userId);
//
//        assertEquals(1, result.size());
//        assertEquals(stores, result);
//    }
//
//    // ===========================
//    // updateStore() 테스트
//    // ===========================
//
//    @Test
//    void updateStore_success() {
//        Long storeId = 1L;
//        var existingStore = CouponStore.builder()
//                .id(storeId)
//                .user(user)
//                .coupon(coupon)
//                .originType(OriginType.WELCOME)
//                .status(CouponStatus.READY)
//                .issuedAt(LocalDateTime.now())
//                .build();
//        when(couponStoreRepository.findById(storeId)).thenReturn(Optional.of(existingStore));
//
//        CouponStoreUpdateRequest updateRequest = new CouponStoreUpdateRequest(CouponStatus.USED, LocalDateTime.now());
//
//        var updated = couponStoreService.updateStore(storeId, updateRequest);
//
//        assertEquals(CouponStatus.USED, updated.getStatus());
//        assertNotNull(updated.getUsedAt());
//    }
//
//    @Test
//    void updateStore_notFound_throwsCouponStoreNotFoundException() {
//        Long storeId = 999L;
//        when(couponStoreRepository.findById(storeId)).thenReturn(Optional.empty());
//
//        CouponStoreUpdateRequest updateRequest = new CouponStoreUpdateRequest(CouponStatus.USED, LocalDateTime.now());
//
//        assertThrows(CouponStoreNotFoundException.class,
//                () -> couponStoreService.updateStore(storeId, updateRequest));
//    }
//
//    // ===========================
//    // deleteStore() 테스트
//    // ===========================
//
//    @Test
//    void deleteStore_success() {
//        Long storeId = 1L;
//        doNothing().when(couponStoreRepository).deleteById(storeId);
//
//        assertDoesNotThrow(() -> couponStoreService.deleteStore(storeId));
//        verify(couponStoreRepository).deleteById(storeId);
//    }
//
//    @Test
//    void deleteStore_notFound_throwsCouponStoreNotFoundException() {
//        Long storeId = 999L;
//        doThrow(new EmptyResultDataAccessException(1))
//                .when(couponStoreRepository).deleteById(storeId);
//
//        assertThrows(CouponStoreNotFoundException.class,
//                () -> couponStoreService.deleteStore(storeId));
//    }
//
//    // =========================================
//    // getApplicableCouponStores() 간단 검증
//    // =========================================
//
//    @Test
//    void getApplicableCouponStores_filtersByDateAndCombinesOrigins() {
//        // 1) BOOK 기반 쿠폰 IDs
//        List<Long> bookCouponIds = List.of(100L);
//        when(bookCouponRepository.findIdsByBookId(BOOK_ID)).thenReturn(bookCouponIds);
//
//        // 1a) BOOK origin: valid vs expired
//        CouponPolicy validPolicy = mock(CouponPolicy.class);
//        when(validPolicy.getDiscountType()).thenReturn(DiscountType.RATE);
//        when(validPolicy.getDiscountValue()).thenReturn(null);
//        when(validPolicy.getDiscountPercentage()).thenReturn(15);
//        when(validPolicy.getMaximumDiscountAmount()).thenReturn(500);
//
//        Coupon validCoupon = mock(Coupon.class);
//        when(validCoupon.getId()).thenReturn(1000L);
//        when(validCoupon.getName()).thenReturn("BookCoupon");
//        when(validCoupon.getIssuableFrom()).thenReturn(now.minusHours(1));
//        when(validCoupon.getExpiresAt()).thenReturn(now.plusDays(1));
//        when(validCoupon.getCouponPolicy()).thenReturn(validPolicy);
//
//        Coupon expiredCoupon = mock(Coupon.class);
//        when(expiredCoupon.getIssuableFrom()).thenReturn(now.minusDays(2));
//        when(expiredCoupon.getExpiresAt()).thenReturn(now.minusDays(1));
//
//        CouponStore validStore = CouponStore.builder()
//                .id(1L)
//                .coupon(validCoupon)
//                .originType(OriginType.BOOK)
//                .status(CouponStatus.READY)
//                .build();
//
//        CouponStore expiredStore = CouponStore.builder()
//                .id(2L)
//                .coupon(expiredCoupon)
//                .originType(OriginType.BOOK)
//                .status(CouponStatus.READY)
//                .build();
//
//        when(couponStoreRepository.findWithCouponByUserAndOriginAndStatus(
//                USER_ID, OriginType.BOOK, bookCouponIds, CouponStatus.READY))
//                .thenReturn(List.of(validStore, expiredStore));
//
//        // 2) CATEGORY 기반
//        Book book = mock(Book.class);
//        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));
//        BookCategory bc = mock(BookCategory.class);
//        Category cat = mock(Category.class);
//        when(bc.getCategory()).thenReturn(cat);
//        when(cat.getId()).thenReturn(5L);
//        when(book.getBookCategories()).thenReturn(List.of(bc));
//        when(categoryRepository.findAllAncestors(5L)).thenReturn(List.of());
//        when(categoryCouponService.getCategoryCouponsWithFetch(Set.of(5L)))
//                .thenReturn(List.of());
//        when(couponStoreRepository.findWithCouponByUserAndOriginAndStatus(
//                USER_ID, OriginType.CATEGORY, List.of(), CouponStatus.READY))
//                .thenReturn(List.of());
//
//        // 3) WELCOME
//        when(couponStoreRepository.findWithCouponByUserAndOriginAndStatus(
//                USER_ID, OriginType.WELCOME, CouponStatus.READY))
//                .thenReturn(List.of());
//
//        // 4) BIRTHDAY
//        when(couponStoreRepository.findWithCouponByUserAndOriginAndStatus(
//                USER_ID, OriginType.BIRTHDAY, CouponStatus.READY))
//                .thenReturn(List.of());
//
//        // 실행
//        List<CouponStoreDto> dtos = couponStoreService.getApplicableCouponStores(USER_ID, BOOK_ID);
//
//        // 검증: validStore 하나만 매핑
//        assertEquals(1, dtos.size());
//        CouponStoreDto dto = dtos.get(0);
//        assertEquals(validStore.getId(), dto.storeId());
//        assertEquals(validCoupon.getId(), dto.couponId());
//        assertEquals("BookCoupon", dto.couponName());
//        assertEquals(CouponStatus.READY, dto.status());
//        assertEquals(DiscountType.RATE, dto.discountType());
//        assertEquals(15, dto.discountPercentage().intValue());
//        assertEquals(500, dto.maximumDiscountAmount().intValue());
//    }
//}
