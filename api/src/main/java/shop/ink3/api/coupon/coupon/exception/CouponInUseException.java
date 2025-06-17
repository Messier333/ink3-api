package shop.ink3.api.coupon.coupon.exception;

public class CouponInUseException extends RuntimeException {
    public CouponInUseException(String message) {
        super(message);
    }
}
