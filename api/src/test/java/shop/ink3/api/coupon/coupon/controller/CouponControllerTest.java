package shop.ink3.api.coupon.coupon.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.coupon.coupon.dto.CouponCreateRequest;
import shop.ink3.api.coupon.coupon.dto.CouponResponse;
import shop.ink3.api.coupon.coupon.dto.CouponResponse.BookInfo;
import shop.ink3.api.coupon.coupon.dto.CouponResponse.CategoryInfo;
import shop.ink3.api.coupon.coupon.dto.CouponUpdateRequest;
import shop.ink3.api.coupon.coupon.service.Impl.CouponServiceImpl;

@WebMvcTest
@ContextConfiguration(classes = CouponController.class)
public class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CouponServiceImpl couponService;

    private static final LocalDateTime now = LocalDateTime.of(2025, 5, 31, 0, 0);
    private static final LocalDateTime expires = now.plusDays(5);

    @Test
    void create() throws Exception {
        // given
        CouponCreateRequest req = new CouponCreateRequest(
                1L, "test",now, expires, true,
                Collections.emptyList(),
                Collections.emptyList()
        );
        CouponResponse resp = new CouponResponse(
                1L,                  // couponId
                "test",              // name
                1L,                  // policyId
                "SPRING_POLICY",    // policyName
                10,
                0,
                now,
                expires,
                true,
                now,
                Collections.emptyList(),
                Collections.emptyList()
        );
        when(couponService.createCoupon(any(CouponCreateRequest.class)))
                .thenReturn(resp);

        // when / then
        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.couponId").value(1))
                .andExpect(jsonPath("$.data.name").value("test"))
                .andExpect(jsonPath("$.data.policyId").value(1))
                .andExpect(jsonPath("$.data.policyName").value("SPRING_POLICY"))
                .andExpect(jsonPath("$.data.discountRate").value(10))
                .andExpect(jsonPath("$.data.discountValue").value(0))
                .andExpect(jsonPath("$.data.issuableFrom").value(now+ ":00"))
                .andExpect(jsonPath("$.data.expiresAt").value(expires + ":00"))
                .andExpect(jsonPath("$.data.createdAt").value(now + ":00"))
                .andExpect(jsonPath("$.data.books").isArray())
                .andExpect(jsonPath("$.data.books").isEmpty())
                .andExpect(jsonPath("$.data.categories").isArray())
                .andExpect(jsonPath("$.data.categories").isEmpty());

        verify(couponService).createCoupon(any(CouponCreateRequest.class));
    }

    @Test
    @DisplayName("POST /coupons - 도서/카테고리 포함해서 생성")
    void createBooksAndCategories() throws Exception {
        // given
        CouponCreateRequest req = new CouponCreateRequest(
                1L, "test-coupon", now, expires, true,
                List.of(100L),
                List.of(200L)
        );
        BookInfo bi = new BookInfo(11L, 100L, "Java Programming", "BOOK");
        CategoryInfo ci = new CategoryInfo(22L, 200L, "Fiction", "CATEGORY");
        CouponResponse resp = new CouponResponse(
                1L,
                "test-coupon",
                1L,
                "SPRING_POLICY",
                10,
                0,
                now,
                expires,
                true,
                now,
                List.of(bi),
                List.of(ci)
        );
        when(couponService.createCoupon(any(CouponCreateRequest.class)))
                .thenReturn(resp);

        // when / then
        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.couponId").value(1))
                .andExpect(jsonPath("$.data.name").value("test-coupon"))
                .andExpect(jsonPath("$.data.policyId").value(1))
                .andExpect(jsonPath("$.data.policyName").value("SPRING_POLICY"))
                .andExpect(jsonPath("$.data.discountRate").value(10))
                .andExpect(jsonPath("$.data.discountValue").value(0))
                .andExpect(jsonPath("$.data.books[0].originId").value(11))
                .andExpect(jsonPath("$.data.books[0].id").value(100))
                .andExpect(jsonPath("$.data.books[0].title").value("Java Programming"))
                .andExpect(jsonPath("$.data.categories[0].originId").value(22))
                .andExpect(jsonPath("$.data.categories[0].id").value(200))
                .andExpect(jsonPath("$.data.categories[0].name").value("Fiction"));

        verify(couponService).createCoupon(any(CouponCreateRequest.class));
    }

    @Test
    @DisplayName("GET /coupons/{couponId} - 단건 조회 성공")
    void getById_success() throws Exception {
        // given
        long id = 1L;
        CouponResponse resp = new CouponResponse(
                id,
                "sample-coupon",
                10L,
                "SUMMER_POLICY",
                10,
                0,
                now,
                expires,
                true,
                now,
                Collections.emptyList(),
                Collections.emptyList()
        );
        when(couponService.getCouponById(id)).thenReturn(resp);

        // when / then
        mockMvc.perform(get("/coupons/{couponId}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.couponId").value(1))
                .andExpect(jsonPath("$.data.name").value("sample-coupon"))
                .andExpect(jsonPath("$.data.policyId").value(10))
                .andExpect(jsonPath("$.data.policyName").value("SUMMER_POLICY"))
                .andExpect(jsonPath("$.data.discountRate").value(10))
                .andExpect(jsonPath("$.data.discountValue").value(0));


        verify(couponService).getCouponById(id);
    }

    @Test
    @DisplayName("GET /coupons - 전체 조회 성공 (페이징)")
    void getAll_success_withPageable() throws Exception {
        // given
        CouponResponse c1 = new CouponResponse(
                1L, "coup1", 10L, "P1", 10, 0, now, expires, true, now,
                Collections.emptyList(), Collections.emptyList()
        );
        CouponResponse c2 = new CouponResponse(
                2L, "coup2", 20L, "P2", 10, 0, now, expires, true, now,
                Collections.emptyList(), Collections.emptyList()
        );
        PageRequest pg = PageRequest.of(0, 2);
        PageResponse<CouponResponse> pageResp = PageResponse.from(
                new PageImpl<>(List.of(c1, c2), pg, 2)
        );
        when(couponService.getAllCoupons(eq(pg))).thenReturn(pageResp);

        // when / then
        mockMvc.perform(get("/coupons")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.content[0].policyName").value("P1"))
                .andExpect(jsonPath("$.data.content[1].policyName").value("P2"));

        verify(couponService).getAllCoupons(eq(pg));
    }

    @Test
    @DisplayName("PUT /coupons/{couponId} - 수정 성공")
    void updateCoupon_success() throws Exception {
        // given
        Long couponId = 5L;
        CouponUpdateRequest req = new CouponUpdateRequest(
                30L, "updated-name", now, expires.plusDays(2), true,
                List.of(100L), List.of(200L)
        );
        BookInfo bi = new BookInfo(11L, 100L, "BookTitle", "BOOK");
        CategoryInfo ci = new CategoryInfo(22L, 200L, "CatName", "CATEGORY");
        CouponResponse resp = new CouponResponse(
                couponId, "updated-name", 30L, "P30", 10, 0,
                now, expires.plusDays(2), true, now,
                List.of(bi), List.of(ci)
        );
        when(couponService.updateCoupon(eq(couponId), any(CouponUpdateRequest.class)))
                .thenReturn(resp);

        // when / then
        mockMvc.perform(put("/coupons/{couponId}", couponId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.name").value("updated-name"))
                .andExpect(jsonPath("$.data.policyName").value("P30"))
                .andExpect(jsonPath("$.data.discountRate").value(10))
                .andExpect(jsonPath("$.data.discountValue").value(0))
                .andExpect(jsonPath("$.data.books[0].id").value(100))
                .andExpect(jsonPath("$.data.categories[0].id").value(200));

        verify(couponService).updateCoupon(eq(couponId), any(CouponUpdateRequest.class));
    }

    @Test
    @DisplayName("DELETE /coupons/{couponId} - 삭제 성공")
    void deleteById_success() throws Exception {
        // when / then
        mockMvc.perform(delete("/coupons/{couponId}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data").isEmpty());

        verify(couponService).deleteCouponById(7L);
    }

    @Test
    @DisplayName("GET /coupons/by-book/{bookId} - 도서별 조회 성공")
    void getByBookId_success_withPageable() throws Exception {
        // given
        Long bookId = 100L;
        BookInfo bi = new BookInfo(11L, 100L, "BookTitle", "BOOK");
        CouponResponse cr = new CouponResponse(
                9L, "from-book", 40L, "PB", 10, 0,
                now, expires, true, now,
                List.of(bi), Collections.emptyList()
        );
        PageRequest pg = PageRequest.of(0, 2);
        PageResponse<CouponResponse> pr = PageResponse.from(
                new PageImpl<>(List.of(cr), pg, 1)
        );
        when(couponService.getCouponsByBookId(eq(bookId), eq(pg))).thenReturn(pr);

        // when / then
        mockMvc.perform(get("/coupons/by-book/{bookId}", bookId)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.content[0].couponId").value(9))
                .andExpect(jsonPath("$.data.content[0].books[0].id").value(100));

        verify(couponService).getCouponsByBookId(eq(bookId), eq(pg));
    }

    @Test
    @DisplayName("GET /coupons/by-category/{categoryId} - 카테고리별 조회 성공")
    void getByCategoryId_success_withPageable() throws Exception {
        // given
        Long categoryId = 200L;
        CategoryInfo ci = new CategoryInfo(22L, 200L, "CatName", "CATEGORY");
        CouponResponse cr = new CouponResponse(
                15L, "from-cat", 50L, "PC", 10, 0,
                now, expires, true, now,
                Collections.emptyList(), List.of(ci)
        );
        PageRequest pg = PageRequest.of(0, 2);
        PageResponse<CouponResponse> pr = PageResponse.from(
                new PageImpl<>(List.of(cr), pg, 1)
        );
        when(couponService.getCouponsByCategoryId(eq(categoryId), eq(pg))).thenReturn(pr);

        // when / then
        mockMvc.perform(get("/coupons/by-category/{categoryId}", categoryId)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.content[0].couponId").value(15))
                .andExpect(jsonPath("$.data.content[0].categories[0].id").value(200));

        verify(couponService).getCouponsByCategoryId(eq(categoryId), eq(pg));
    }

    @Test
    @DisplayName("GET /coupons/by-book/{bookId}/parent-categories - 부모 카테고리 쿠폰 조회")
    void getParentCategoryCoupons_success() throws Exception {
        // given
        Long bookId = 150L;
        CategoryInfo ci = new CategoryInfo(
                55L,        // CategoryCoupon ID
                300L,       // parent category ID
                "Non-fiction", // parent category name
                "PARENT_CATEGORY"
        );
        CouponResponse cr = new CouponResponse(
                20L,                         // couponId
                "parent-coupon",             // name
                70L,                         // policyId
                "PCP",                       // policyName
                15,                          // discountRate
                0,                           // discountValue
                now,                         // issuableFrom
                expires,
                true,
                now,                         // createdAt
                Collections.emptyList(),     // books
                List.of(ci)                  // categories
        );
        PageRequest pg = PageRequest.of(1, 5);
        PageResponse<CouponResponse> pr = PageResponse.from(
                new PageImpl<>(List.of(cr), pg, 1)
        );
        when(couponService.getCouponsByParentId(eq(bookId), eq(pg)))
                .thenReturn(pr);

        // when / then
        mockMvc.perform(get("/coupons/by-book/{bookId}/parent-categories", bookId)
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.content", hasSize(1)))
                .andExpect(jsonPath("$.data.content[0].couponId").value(20))
                .andExpect(jsonPath("$.data.content[0].categories[0].id").value(300))
                .andExpect(jsonPath("$.data.content[0].categories[0].name").value("Non-fiction"));

        verify(couponService).getCouponsByParentId(eq(bookId), eq(pg));
    }

}
