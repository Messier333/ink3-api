package shop.ink3.api.payment.dto;

import shop.ink3.api.payment.entity.PaymentType;

public record PaymentCancelRequest(
        Long orderId,
        String paymentKey,
        Integer amount,
        PaymentType paymentType,
        String cancelReason
){
}