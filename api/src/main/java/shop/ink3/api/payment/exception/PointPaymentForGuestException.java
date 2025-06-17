package shop.ink3.api.payment.exception;

import shop.ink3.api.common.exception.BadRequestException;

public class PointPaymentForGuestException extends BadRequestException {

    public PointPaymentForGuestException() {
        super("Guest users are not allowed to use point payment.");
    }

    public PointPaymentForGuestException(long orderId) {
        super("Guest users are not allowed to use point payment. (Order ID: %d)".formatted(orderId));
    }
}
