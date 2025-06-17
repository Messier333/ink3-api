package shop.ink3.api.payment.exception;

import shop.ink3.api.common.exception.NotFoundException;

public class PaymentKeyNotExistsException extends NotFoundException {
    public PaymentKeyNotExistsException() {
        super("PaymentKey Not Exists about this Order.");
    }

    public PaymentKeyNotExistsException(long orderId) {
        super("PaymentKey Not Exists. Order ID: %d".formatted(orderId));
    }

}
