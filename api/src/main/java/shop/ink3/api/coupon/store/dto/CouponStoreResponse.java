package shop.ink3.api.coupon.store.dto;

import java.time.LocalDateTime;

import lombok.Builder;

import org.antlr.v4.runtime.atn.SemanticContext.OR;

import shop.ink3.api.coupon.coupon.entity.Coupon;
import shop.ink3.api.coupon.store.entity.CouponStatus;
import shop.ink3.api.coupon.store.entity.CouponStore;
import shop.ink3.api.coupon.store.entity.OriginType;

@Builder // 발급 response
public record CouponStoreResponse(
    Long storeId,
    Long userId,
    String userName,
    Long couponId,
    String couponName,
    OriginType originType,
    Long originId,
    CouponStatus status,
    LocalDateTime issuedAt,
    LocalDateTime expiresAt,
    Integer discountValue,
    Integer discountPercentage
) {
    public static CouponStoreResponse fromEntity(CouponStore couponStore) {
        return new CouponStoreResponse(
            couponStore.getId(),
            couponStore.getUser().getId(),
            couponStore.getUser().getName(),
            couponStore.getCoupon().getId(),
            couponStore.getCoupon().getName(),
            couponStore.getOriginType(),
            couponStore.getOriginId(),
            couponStore.getStatus(),
            couponStore.getIssuedAt(),
            null,
            0,
            0
        );
    }

    public static CouponStoreResponse toEntity(CouponStore couponStore) {
        return new CouponStoreResponse(
            couponStore.getId(),
            couponStore.getUser().getId(),
            couponStore.getUser().getName(),
            couponStore.getCoupon().getId(),
            couponStore.getCoupon().getName(),
            couponStore.getOriginType(),
            couponStore.getOriginId(),
            couponStore.getStatus(),
            couponStore.getIssuedAt(),
            couponStore.getCoupon().getExpiresAt(),
            couponStore.getCoupon().getCouponPolicy().getDiscountValue(),
            couponStore.getCoupon().getCouponPolicy().getDiscountPercentage()
        );
    }
}

