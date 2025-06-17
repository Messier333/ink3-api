package shop.ink3.api.payment.paymentUtil.processor;

import shop.ink3.api.order.guest.dto.GuestPaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentCancelRequest;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;

public interface PaymentProcessor {
    String processPayment(PaymentConfirmRequest request);
    String processPayment(GuestPaymentConfirmRequest request);

    String cancelPayment(PaymentCancelRequest request);
}
