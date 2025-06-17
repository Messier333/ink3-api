package shop.ink3.api.payment.paymentUtil.parser.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.ink3.api.order.guest.dto.GuestPaymentConfirmRequest;
import shop.ink3.api.order.order.entity.Order;
import shop.ink3.api.order.order.exception.OrderNotFoundException;
import shop.ink3.api.order.order.repository.OrderRepository;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.entity.Payment;
import shop.ink3.api.payment.entity.PaymentType;
import shop.ink3.api.payment.exception.PaymentParserFailException;
import shop.ink3.api.payment.exception.PointPaymentForGuestException;
import shop.ink3.api.payment.paymentUtil.parser.PaymentParser;

@RequiredArgsConstructor
@Component("POINT-PARSER")
public class PointPaymentParser implements PaymentParser {
    private final OrderRepository orderRepository;
    private static final String PAYMENT_METHOD = "POINT";

    @Override
    public Payment paymentResponseParser(PaymentConfirmRequest request, String json) {
        try {
            Order order = orderRepository.findById(request.orderId())
                    .orElseThrow(() -> new OrderNotFoundException(request.orderId()));
            return Payment.builder()
                    .order(order)
                    .paymentKey(null)
                    .usedPoint(request.usedPointAmount())
                    .discountPrice(request.discountAmount())
                    .paymentAmount(request.amount())
                    .paymentType(PaymentType.POINT)
                    .requestAt(LocalDateTime.now())
                    .approvedAt(LocalDateTime.now())
                    .build();
        }catch (Exception e) {
            throw new PaymentParserFailException(PAYMENT_METHOD, e);
        }
    }

    // 비회원의 경우 0원 결제 불가능
    @Override
    public Payment paymentResponseParser(GuestPaymentConfirmRequest paymentConfirmRequest, String json) {
        throw new PointPaymentForGuestException(paymentConfirmRequest.orderId());
    }
}
