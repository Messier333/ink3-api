package shop.ink3.api.coupon.store.dto;

import jakarta.validation.constraints.NotNull;
import shop.ink3.api.coupon.store.dto.valid.ValidOriginId;
import shop.ink3.api.coupon.store.entity.OriginType;

@ValidOriginId
public record CommonCouponIssueRequest(
        @NotNull(message = "userId는 필수입니다.") Long userId,
        @NotNull(message = "couponId는 필수입니다.") Long couponId,
        @NotNull OriginType originType,
        Long originId
) {}
