package shop.ink3.api.coupon.store.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.coupon.store.dto.CouponIssueRequest;
import shop.ink3.api.coupon.store.dto.CouponStoreDto;
import shop.ink3.api.coupon.store.dto.CouponStoreResponse;
import shop.ink3.api.coupon.store.dto.CouponStoreUpdateRequest;
import shop.ink3.api.coupon.store.dto.CouponStoreUpdateResponse;
import shop.ink3.api.coupon.store.entity.CouponStore;
import shop.ink3.api.coupon.store.service.CouponStoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class MeCouponStoreController {

    private final CouponStoreService couponStoreService;

    // 쿠폰 발급 (store 생성)
    @PostMapping("/users/coupon-stores")
    public ResponseEntity<CommonResponse<CouponStoreResponse>> issueCoupon(
            @RequestHeader(name = "X-User-Id") Long userId,
            @RequestBody CouponIssueRequest request) {
        CouponStoreResponse response = CouponStoreResponse.fromEntity(couponStoreService.issueCoupon(request, userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.create(response));
    }

    // ✅ 유저의 전체 쿠폰 조회 → /users/{userId}/stores
    @GetMapping("/users/coupon-stores")
    public ResponseEntity<CommonResponse<PageResponse<CouponStoreResponse>>> getStoresByUserId(
        @RequestHeader(name = "X-User-Id") Long userId,
        @PageableDefault(sort = "issuedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CouponStore> stores = couponStoreService.getStoresPagingByUserId(userId, pageable);
        Page<CouponStoreResponse> mapped = stores.map(CouponStoreResponse::toEntity);
        return ResponseEntity.ok(CommonResponse.success(PageResponse.from(mapped)));
    }

    // ✅ 유저의 미사용 쿠폰만 조회
    @GetMapping("/users/coupon-stores/status-unused")
    public ResponseEntity<CommonResponse<PageResponse<CouponStoreResponse>>> getUnusedStores(
        @RequestHeader(name = "X-User-Id") Long userId,
        @PageableDefault(sort = "usedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CouponStore> stores = couponStoreService.getUnusedStoresPagingByUserId(userId, pageable);
        Page<CouponStoreResponse> mapped = stores.map(CouponStoreResponse::toEntity);
        return ResponseEntity.ok(CommonResponse.success(PageResponse.from(mapped)));
    }

    // ✅ 유저의 사용/만료된 쿠폰만 조회
    @GetMapping("/users/coupon-stores/status-used")
    public ResponseEntity<CommonResponse<PageResponse<CouponStoreResponse>>> getUsedExpiredStores(
        @RequestHeader(name = "X-User-Id") Long userId,
        @PageableDefault(sort = "usedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CouponStore> stores = couponStoreService.getUsedOrExpiredStoresPagingByUserId(userId, pageable);
        Page<CouponStoreResponse> mapped = stores.map(CouponStoreResponse::toEntity);
        return ResponseEntity.ok(CommonResponse.success(PageResponse.from(mapped)));
    }

    // ✅ 특정 쿠폰으로 발급된 store 전체 조회 → /coupons/{couponId}/stores
    @GetMapping("/coupons/{couponId}/coupon-stores")
    public ResponseEntity<CommonResponse<List<CouponStoreResponse>>> getStoresByCouponId(@PathVariable Long couponId) {
        List<CouponStore> stores = couponStoreService.getStoresByCouponId(couponId);
        List<CouponStoreResponse> responses = stores.stream()
            .map(CouponStoreResponse::fromEntity)
            .toList();
        return ResponseEntity.ok(CommonResponse.success(responses));
    }



    // ✅ 쿠폰 사용 여부 업데이트
    @PutMapping("/coupon-stores/{storeId}")
    public ResponseEntity<CommonResponse<CouponStoreUpdateResponse>> updateStore(
        @PathVariable Long storeId,
        @RequestBody CouponStoreUpdateRequest request) {
        CouponStoreUpdateResponse response = CouponStoreUpdateResponse.of(
            couponStoreService.updateStore(storeId, request)
        );
        return ResponseEntity.ok(CommonResponse.update(response));
    }

    // ✅ 쿠폰 발급 삭제
    @DeleteMapping("/coupon-stores/{storeId}")
    public ResponseEntity<CommonResponse<Void>> deleteStore(@PathVariable Long storeId) {
        couponStoreService.deleteStore(storeId);
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @GetMapping("/applicable-coupons")
    public ResponseEntity<List<CouponStoreDto>> getApplicableCoupons(
        @RequestParam Long userId,
        @RequestParam Long bookId
    ) {
        List<CouponStoreDto> stores = couponStoreService.getApplicableCouponStores(userId, bookId);
        return ResponseEntity.ok(stores);
    }

}

