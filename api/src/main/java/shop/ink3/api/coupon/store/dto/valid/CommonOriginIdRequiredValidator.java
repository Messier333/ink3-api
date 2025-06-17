package shop.ink3.api.coupon.store.dto.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import shop.ink3.api.coupon.store.dto.CommonCouponIssueRequest;
import shop.ink3.api.coupon.store.entity.OriginType;

public class CommonOriginIdRequiredValidator
        implements ConstraintValidator<ValidOriginId, CommonCouponIssueRequest> {

    @Override
    public boolean isValid(CommonCouponIssueRequest value, ConstraintValidatorContext context) {
        if (value == null || value.originType() == null) {
            return true; // @NotNull on originType handles null‐type
        }

        if ((value.originType() == OriginType.BOOK || value.originType() == OriginType.CATEGORY)
                && value.originId() == null) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("originId는 BOOK 또는 CATEGORY 타입일 경우 필수입니다.")
                    .addPropertyNode("originId")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}

