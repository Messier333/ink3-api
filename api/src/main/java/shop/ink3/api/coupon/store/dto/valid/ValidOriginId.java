package shop.ink3.api.coupon.store.dto.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {
        OriginIdRequiredValidator.class,
        CommonOriginIdRequiredValidator.class})
@Documented
public @interface ValidOriginId {
    String message() default "originId는 BOOK 또는 CATEGORY 타입일 때 필수입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
