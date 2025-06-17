package shop.ink3.api.payment.dto;

import java.time.LocalDateTime;
import shop.ink3.api.payment.entity.Payment;
import shop.ink3.api.payment.entity.PaymentType;

public record PaymentResponse (
    long id,
    long orderId,
    String paymentKey,
    int usedPointAmount,
    int discountAmount,
    int paymentAmount,
    PaymentType paymentType,
    LocalDateTime requestedAt,
    LocalDateTime approvedAt
){
    public static PaymentResponse from(Payment payment){
        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentKey(),
                payment.getUsedPoint(),
                payment.getDiscountPrice(),
                payment.getPaymentAmount(),
                payment.getPaymentType(),
                payment.getRequestAt(),
                payment.getApprovedAt()
        );
    }
}
