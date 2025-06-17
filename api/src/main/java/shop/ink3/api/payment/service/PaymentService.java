package shop.ink3.api.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.ink3.api.coupon.store.dto.CouponStoreUpdateRequest;
import shop.ink3.api.coupon.store.entity.CouponStatus;
import shop.ink3.api.coupon.store.service.CouponStoreService;
import shop.ink3.api.order.order.dto.OrderResponse;
import shop.ink3.api.order.order.dto.OrderStatusUpdateRequest;
import shop.ink3.api.order.order.entity.Order;
import shop.ink3.api.order.order.entity.OrderStatus;
import shop.ink3.api.order.order.exception.OrderNotFoundException;
import shop.ink3.api.order.order.repository.OrderRepository;
import shop.ink3.api.order.order.service.OrderService;
import shop.ink3.api.order.orderBook.service.OrderBookService;
import shop.ink3.api.order.orderPoint.entity.OrderPoint;
import shop.ink3.api.order.orderPoint.service.OrderPointService;
import shop.ink3.api.payment.dto.PaymentCancelRequest;
import shop.ink3.api.payment.dto.PaymentConfirmRequest;
import shop.ink3.api.payment.dto.PaymentResponse;
import shop.ink3.api.payment.dto.ZeroPaymentRequest;
import shop.ink3.api.payment.entity.Payment;
import shop.ink3.api.payment.entity.PaymentType;
import shop.ink3.api.payment.exception.PaymentAlreadyExistsException;
import shop.ink3.api.payment.exception.PaymentCancelNotAllowedException;
import shop.ink3.api.payment.exception.PaymentNotFoundException;
import shop.ink3.api.payment.paymentUtil.parser.PaymentParser;
import shop.ink3.api.payment.paymentUtil.processor.PaymentProcessor;
import shop.ink3.api.payment.paymentUtil.resolver.PaymentProcessorResolver;
import shop.ink3.api.payment.paymentUtil.resolver.PaymentResponseParserResolver;
import shop.ink3.api.payment.repository.PaymentRepository;
import shop.ink3.api.user.point.history.eventListener.PointHistoryAfterPaymentEven;
import shop.ink3.api.user.point.history.service.PointService;
import shop.ink3.api.user.user.dto.UserPointRequest;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class PaymentService {
    private static final String PAYMENT_CANCEL_MESSAGE ="결제 취소로 인한 환불금액";

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private final OrderService orderService;
    private final OrderBookService orderBookService;
    private final OrderPointService orderPointService;
    private final PointService pointService;
    private final CouponStoreService couponStoreService;

    private final PaymentProcessorResolver paymentProcessorResolver;
    private final PaymentResponseParserResolver paymentResponseParserResolver;
    private final ApplicationEventPublisher eventPublisher;

    // 조회
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
        return PaymentResponse.from(payment);
    }

    // 삭제
    public void deletePayment(long orderId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        paymentRepository.deleteByOrderId(orderId);
    }

    // 결제 승인 API 호출 및 ApproveResponse 반환
    @Transactional(readOnly = true)
    public String callPaymentAPI(PaymentConfirmRequest confirmRequest) {
        PaymentProcessor paymentProcessor = paymentProcessorResolver.getPaymentProcessor(
                String.format("%s-%s", String.valueOf(confirmRequest.paymentType()).toUpperCase(), "PROCESSOR"));
        return paymentProcessor.processPayment(confirmRequest);
    }

    // 생성 (결제 성공)
    public PaymentResponse createPayment(PaymentConfirmRequest confirmRequest, String paymentApproveResponse) {
        if (paymentRepository.findByOrderId(confirmRequest.orderId()).isPresent()) {
            throw new PaymentAlreadyExistsException(confirmRequest.orderId());
        }

        // 외부 api 파싱 -> payment 객체 생성
        PaymentParser paymentParser = paymentResponseParserResolver.getPaymentParser(
                String.format("%s-%s", String.valueOf(confirmRequest.paymentType()).toUpperCase(), "PARSER"));
        Payment payment = paymentParser.paymentResponseParser(confirmRequest, paymentApproveResponse);

        // 주문 상태 변경 및 결제 저장
        orderService.updateOrderStatus(confirmRequest.orderId(), new OrderStatusUpdateRequest(OrderStatus.CONFIRMED));
        Payment savePayment = paymentRepository.save(payment);

        // 0원 결제일 경우 분기문 처리 (포인트 적립 X )
        if(!payment.getPaymentType().equals(PaymentType.POINT) && confirmRequest.userId()!=null){
            // 비동기 이벤트 핸들러 ( 포인트 사용 내역 및 적립 내역 추가 )
            eventPublisher.publishEvent(new PointHistoryAfterPaymentEven(
                    confirmRequest.userId(),
                    confirmRequest.orderId(),
                    confirmRequest.amount(),
                    confirmRequest.usedPointAmount())
            );
        }
        return PaymentResponse.from(savePayment);
    }

    // 결제 실패
    public void failPayment(long orderId, Long userId) {
        // 주문된 도서들의 재고를 원상복구
        orderBookService.resetBookQuantity(orderId);
        // 주문 상태 변경
        orderService.updateOrderStatus(orderId, new OrderStatusUpdateRequest(OrderStatus.FAILED));

        if(Objects.nonNull(userId)){
            // 사용된 쿠폰 되돌리기 (포인트는 결제 후 이기 때문에 처리 X)
            orderBookService.getOrderCouponStoreId(orderId)
                    .ifPresent(couponStoreId -> {
                        CouponStoreUpdateRequest request = new CouponStoreUpdateRequest(CouponStatus.READY, null);
                        couponStoreService.updateStore(couponStoreId, request);
                    });
        }
    }

    // 결제 취소
    public void cancelPayment(long orderId, Long userId, PaymentCancelRequest cancelRequest) {
        // 결제 취소 가능 여부 확인
        OrderResponse orderResponse = orderService.getOrder(orderId);
        if (!orderResponse.getStatus().equals(OrderStatus.CONFIRMED)) {
            throw new PaymentCancelNotAllowedException();
        }

        // 금액 환불
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
        // 외부 API 결제 -> 결제 취소 요청
        PaymentProcessor paymentProcessor = paymentProcessorResolver.getPaymentProcessor(
                String.format("%s-%s", String.valueOf(payment.getPaymentType()).toUpperCase(), "PROCESSOR"));
        paymentProcessor.cancelPayment(cancelRequest);

        // 주문된 도서들의 재고를 원상복구
        orderBookService.resetBookQuantity(orderId);
        // 주문 상태 변경
        orderService.updateOrderStatus(orderId, new OrderStatusUpdateRequest(OrderStatus.CANCELLED));

        if(Objects.nonNull(userId)){
            // 사용된 쿠폰 되돌리기
            orderBookService.getOrderCouponStoreId(orderId)
                    .ifPresent(couponStoreId -> {
                        CouponStoreUpdateRequest request = new CouponStoreUpdateRequest(CouponStatus.READY, null);
                        couponStoreService.updateStore(couponStoreId, request);
                    });

            // 포인트 취소 (사용한 것도 취소 적립된 것도 취소)
            List<OrderPoint> orderPoints = orderPointService.getOrderPoints(orderId);
            for (OrderPoint orderPoint : orderPoints) {
                pointService.cancelPoint(userId, orderPoint.getPointHistory().getId());
            }
            // 0원 결제 시 -> 전체 환불
            if(payment.getPaymentType().equals(PaymentType.POINT)) {
                pointService.earnPoint(userId, new UserPointRequest(payment.getPaymentAmount(), PAYMENT_CANCEL_MESSAGE));
            }
        }
    }
}
