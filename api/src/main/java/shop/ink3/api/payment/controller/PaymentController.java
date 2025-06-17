package shop.ink3.api.payment.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ink3.api.common.dto.CommonResponse;
import shop.ink3.api.order.order.dto.OrderStatusUpdateRequest;
import shop.ink3.api.order.order.entity.OrderStatus;
import shop.ink3.api.order.order.service.OrderService;
import shop.ink3.api.payment.dto.PaymentCancelRequest;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentResponse;
import shop.ink3.api.payment.dto.ZeroPaymentRequest;
import shop.ink3.api.payment.entity.PaymentType;
import shop.ink3.api.payment.exception.PaymentKeyNotExistsException;
import shop.ink3.api.payment.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    // 결제 승인 API 호출 및 결과 저장
    @PostMapping("/confirm")
    public ResponseEntity<CommonResponse<PaymentResponse>> confirmPayment(
            @RequestBody PaymentConfirmRequest confirmRequest) {
        if(!confirmRequest.paymentType().equals(PaymentType.POINT) && Objects.isNull(confirmRequest.paymentKey())) {
                throw new PaymentKeyNotExistsException(confirmRequest.orderId());
        }
        String paymentApproveResponse = paymentService.callPaymentAPI(confirmRequest);
        PaymentResponse paymentResponse = paymentService.createPayment(confirmRequest, paymentApproveResponse);
        return ResponseEntity.ok(CommonResponse.success(paymentResponse));
    }

    // 결제 실패 처리 (회원)
    @PostMapping("/{orderId}/fail")
    public ResponseEntity<CommonResponse<Void>> failPayment(
            @PathVariable long orderId,
            @RequestHeader(value = "X-User-Id") long userId){
        paymentService.failPayment(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    // 결제 취소
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<CommonResponse<Void>> cancelPayment(
            @PathVariable long orderId,
            @RequestHeader("X-User-Id") long userId,
            @RequestBody PaymentCancelRequest cancelRequest) {
        paymentService.cancelPayment(orderId, userId, cancelRequest);
        return ResponseEntity.noContent().build();
    }

    // 결제 결과 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse<PaymentResponse>> getPayment(@PathVariable long orderId) {
        return ResponseEntity
                .ok(CommonResponse.success(paymentService.getPayment(orderId)));
    }

    // 결제 내역 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse<Void>> deletePayment(@PathVariable long orderId) {
        paymentService.deletePayment(orderId);
        return ResponseEntity.noContent().build();
    }
}
