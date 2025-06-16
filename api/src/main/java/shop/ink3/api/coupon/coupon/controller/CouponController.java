package shop.ink3.api.coupon.coupon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.coupon.coupon.dto.CouponCreateRequest;
import shop.ink3.api.coupon.coupon.dto.CouponResponse;
import shop.ink3.api.coupon.coupon.dto.CouponUpdateRequest;
import shop.ink3.api.coupon.coupon.service.Impl.CouponServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponServiceImpl couponService;

    /** 쿠폰 생성 */
    @PostMapping
    public ResponseEntity<CommonResponse<CouponResponse>> create(@RequestBody @Valid CouponCreateRequest request) {
        CouponResponse coupon = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.create(coupon));
    }

    /** ID로 쿠폰 단건 조회 */
    @GetMapping("/{couponId}")
    public ResponseEntity<CommonResponse<CouponResponse>> getById(@PathVariable long couponId) {
        CouponResponse resp = couponService.getCouponById(couponId);
        return ResponseEntity.ok(CommonResponse.success(resp));
    }

    /** 전체 쿠폰 조회 */
    @GetMapping
    public ResponseEntity<CommonResponse<PageResponse<CouponResponse>>> getAll(Pageable pageable) {
        PageResponse<CouponResponse> list = couponService.getAllCoupons(pageable);
        return ResponseEntity.ok(CommonResponse.success(list));
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<CommonResponse<CouponResponse>> updateCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid CouponUpdateRequest request) {

        CouponResponse updated = couponService.updateCoupon(couponId, request);
        return ResponseEntity.ok(CommonResponse.success(updated));
    }

    /** 쿠폰 ID로 삭제 */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<CommonResponse<Void>> deleteById(@PathVariable long couponId) {
        couponService.deleteCouponById(couponId);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    /** book ID로 쿠폰 조회 */
    @GetMapping("/by-book/{bookId}")
    public ResponseEntity<CommonResponse<PageResponse<CouponResponse>>> getByBookId(
            @PathVariable long bookId,
            Pageable pageable) {
        PageResponse<CouponResponse> list = couponService.getCouponsByBookId(bookId, pageable);
        return ResponseEntity.ok(CommonResponse.success(list));
    }

    /** category ID로 쿠폰 조회 */
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<CommonResponse<PageResponse<CouponResponse>>> getByCategoryId(
            @PathVariable long categoryId,
            Pageable pageable) {
        PageResponse<CouponResponse> list = couponService.getCouponsByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(CommonResponse.success(list));
    }

    /** 부모 카테고리 쿠폰 조회 */
    @GetMapping("/by-book/{bookId}/parent-categories")
    public ResponseEntity<CommonResponse<PageResponse<CouponResponse>>> getParentCategoryCoupons(
            @PathVariable Long bookId,
            Pageable pageable
    ) {
        PageResponse<CouponResponse> list = couponService.getCouponsByParentId(bookId, pageable);
        return ResponseEntity.ok(CommonResponse.success(list));
    }

}



