package shop.ink3.api.order.guest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.order.guest.dto.GuestPaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentResponse;
import shop.ink3.api.payment.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/guest-payment")
public class GuestPaymentController {
    private final PaymentService paymentService;


    // 결제 승인 API 호출 및 결과 저장
    @PostMapping("/confirm")
    public ResponseEntity<CommonResponse<PaymentResponse>> confirmPayment(
            @RequestBody GuestPaymentConfirmRequest confirmRequest
    ) {
        PaymentConfirmRequest paymentConfirmRequest = new PaymentConfirmRequest(
                null,
                confirmRequest.orderId(),
                confirmRequest.paymentKey(),
                confirmRequest.orderUUID(),
                0,
                0,
                confirmRequest.amount(),
                confirmRequest.paymentType()
        );
        String paymentApproveResponse = paymentService.callPaymentAPI(paymentConfirmRequest);
        PaymentResponse paymentResponse = paymentService.createPayment(paymentConfirmRequest, paymentApproveResponse);
        return ResponseEntity.ok(CommonResponse.success(paymentResponse));
    }

    // 결제 실패 처리
    @PostMapping("/{orderId}/fail")
    public ResponseEntity<CommonResponse<Void>> failPayment(@PathVariable long orderId){
        paymentService.failPayment(orderId, null);
        return ResponseEntity.noContent().build();
    }
}
