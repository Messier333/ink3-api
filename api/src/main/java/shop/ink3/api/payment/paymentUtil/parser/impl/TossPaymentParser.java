package shop.ink3.api.payment.paymentUtil.parser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.ink3.api.order.guest.dto.GuestPaymentConfirmRequest;
import shop.ink3.api.order.order.entity.Order;
import shop.ink3.api.order.order.exception.OrderNotFoundException;
import shop.ink3.api.order.order.repository.OrderRepository;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.dto.TossPaymentResponse;
import shop.ink3.api.payment.entity.Payment;
import shop.ink3.api.payment.entity.PaymentType;
import shop.ink3.api.payment.exception.PaymentParserFailException;
import shop.ink3.api.payment.paymentUtil.parser.PaymentParser;

@RequiredArgsConstructor
@Component("TOSS-PARSER")
public class TossPaymentParser implements PaymentParser {
    private static final String PAYMENT_METHOD = "TOSS";
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public Payment paymentResponseParser(PaymentConfirmRequest request, String json) {
        return createPayment(request.orderId(), request.usedPointAmount(), request.discountAmount(), json);
    }

    @Override
    public Payment paymentResponseParser(GuestPaymentConfirmRequest request, String json) {
        return createPayment(request.orderId(), 0, 0, json);
    }

    private Payment createPayment(long orderId, int usedPoint, int discountPrice, String json) {
        try {
            TossPaymentResponse tossResponse = objectMapper.readValue(json, TossPaymentResponse.class);
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException(orderId));

            return Payment.builder()
                    .order(order)
                    .paymentKey(tossResponse.paymentKey())
                    .usedPoint(usedPoint)
                    .discountPrice(discountPrice)
                    .paymentAmount(tossResponse.totalAmount())
                    .paymentType(PaymentType.TOSS)
                    .requestAt(tossResponse.requestedAt().toLocalDateTime())
                    .approvedAt(tossResponse.approvedAt().toLocalDateTime())
                    .build();
        } catch (Exception e) {
            throw new PaymentParserFailException(PAYMENT_METHOD, e);
        }
    }
}
