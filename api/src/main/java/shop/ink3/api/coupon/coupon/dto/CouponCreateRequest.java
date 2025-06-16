package shop.ink3.api.coupon.coupon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record CouponCreateRequest(
        @NotNull Long policyId,
        @NotBlank String name,
        @NotNull LocalDateTime issuableFrom,
        @NotNull LocalDateTime expiresAt,
        boolean isActive,
        List<Long> bookIdList,
        List<Long> categoryIdList
) { }
