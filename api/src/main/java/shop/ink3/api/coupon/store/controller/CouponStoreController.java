package shop.ink3.api.coupon.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.coupon.bookCoupon.entity.BookCouponRepository;
import shop.ink3.api.coupon.categoryCoupon.entity.CategoryCouponRepository;
import shop.ink3.api.coupon.store.dto.CouponIssueRequest;
import shop.ink3.api.coupon.store.dto.CouponStoreDto;
import shop.ink3.api.coupon.store.dto.CouponStoreResponse;
import shop.ink3.api.coupon.store.dto.CouponStoreUpdateRequest;
import shop.ink3.api.coupon.store.dto.CouponStoreUpdateResponse;
import shop.ink3.api.coupon.store.entity.CouponStore;
import shop.ink3.api.coupon.store.service.CouponStoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponStoreController {

    private final CouponStoreService couponStoreService;

    // ✅ 유저의 전체 쿠폰 조회 → /users/{userId}/stores
    @GetMapping("/users/{userId}/coupon-stores")
    public ResponseEntity<CommonResponse<List<CouponStoreResponse>>> getStoresByUserId(@PathVariable Long userId) {
        List<CouponStore> stores = couponStoreService.getStoresByUserId(userId);
        List<CouponStoreResponse> responses = stores.stream()
                .map(CouponStoreResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(CommonResponse.success(responses));
    }

    // ✅ 유저의 미사용 쿠폰만 조회 → /users/{userId}/stores/unused
    @GetMapping("/users/{userId}/coupon-stores/status-unused")
    public ResponseEntity<CommonResponse<List<CouponStoreResponse>>> getUnusedStores(@PathVariable Long userId) {
        List<CouponStore> stores = couponStoreService.getUnusedStoresByUserId(userId);
        List<CouponStoreResponse> responses = stores.stream()
                .map(CouponStoreResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(CommonResponse.success(responses));
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

    @GetMapping("/coupon-stores/exists")
    public ResponseEntity<Boolean> existStoreByOriginId(
            @RequestParam Long userId,
            @RequestParam Long originId) {
        boolean exists = couponStoreService.existByOriginIdAndUserId(userId, originId);
        return ResponseEntity.ok(exists);
    }
}

