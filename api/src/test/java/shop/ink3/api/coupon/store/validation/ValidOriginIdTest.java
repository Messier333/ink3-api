package shop.ink3.api.coupon.store.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shop.ink3.api.coupon.store.dto.CommonCouponIssueRequest;
import shop.ink3.api.coupon.store.dto.CouponIssueRequest;
import shop.ink3.api.coupon.store.entity.OriginType;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidOriginIdTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void bookType() {
        CouponIssueRequest request = new CouponIssueRequest(
                1L,
                OriginType.BOOK,
                null // originId 없음
        );

        Set<ConstraintViolation<CouponIssueRequest>> violations = validator.validate(request);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("originId")
                        && v.getMessage().contains("필수입니다"));
    }

    @Test
    void categoryType() {
        CouponIssueRequest request = new CouponIssueRequest(
                1L,
                OriginType.CATEGORY,
                null
        );

        Set<ConstraintViolation<CouponIssueRequest>> violations = validator.validate(request);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("originId")
                        && v.getMessage().contains("필수입니다"));
    }

    @Test
    void welcomeType() {
        CommonCouponIssueRequest request = new CommonCouponIssueRequest(
                1L,
                1L,
                OriginType.WELCOME,
                null
        );

        Set<ConstraintViolation<CommonCouponIssueRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void birthdayType() {
        CommonCouponIssueRequest request = new CommonCouponIssueRequest(
                1L,
                1L,
                OriginType.BIRTHDAY,
                null
        );

        Set<ConstraintViolation<CommonCouponIssueRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    void bookTypeSuccess() {
        CouponIssueRequest request = new CouponIssueRequest(
                1L,
                OriginType.BOOK,
                99L
        );

        Set<ConstraintViolation<CouponIssueRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}
