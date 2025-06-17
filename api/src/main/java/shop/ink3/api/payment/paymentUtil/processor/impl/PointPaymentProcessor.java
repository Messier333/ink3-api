package shop.ink3.api.payment.paymentUtil.processor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.ink3.api.order.guest.dto.GuestPaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentCancelRequest;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.exception.PaymentKeyNotExistsException;
import shop.ink3.api.payment.paymentUtil.processor.PaymentProcessor;

//TODO : dooray 메신저로 관리자에게 포인트 결제 취소 메세지를 보내면 좋을거 같음.
// POINT의 경우 외부 API에 요청이 필요없기 때문.
@RequiredArgsConstructor
@Component("POINT-PROCESSOR")
public class PointPaymentProcessor implements PaymentProcessor {

    @Override
    public String processPayment(PaymentConfirmRequest request) {
        return null;
    }

    // 비회원의 경우 0원 결제가 불가능하기 때문에 에러 발생.
    @Override
    public String processPayment(GuestPaymentConfirmRequest request) {
        throw new PaymentKeyNotExistsException(request.orderId());
    }

    @Override
    public String cancelPayment(PaymentCancelRequest request) {
        return null;
    }
}
