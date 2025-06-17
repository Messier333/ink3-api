package shop.ink3.api.order.shipment.service;

import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.ink3.api.common.dto.PageResponse;
import shop.ink3.api.order.order.entity.Order;
import shop.ink3.api.order.order.entity.OrderStatus;
import shop.ink3.api.order.order.exception.OrderNotFoundException;
import shop.ink3.api.order.order.repository.OrderRepository;
import shop.ink3.api.order.refundPolicy.repository.RefundPolicyRepository;
import shop.ink3.api.order.shipment.dto.ShipmentCreateRequest;
import shop.ink3.api.order.shipment.dto.ShipmentResponse;
import shop.ink3.api.order.shipment.dto.ShipmentUpdateRequest;
import shop.ink3.api.order.shipment.entity.Shipment;
import shop.ink3.api.order.shipment.exception.ShipmentNotFoundException;
import shop.ink3.api.order.shipment.repository.ShipmentRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;
    private final RefundPolicyRepository refundPolicyRepository;

    // 생성
    public ShipmentResponse createShipment(long orderId, ShipmentCreateRequest request) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException(orderId);
        }
        Order order = optionalOrder.get();
        Shipment shipment = Shipment.builder()
                .order(order)
                .preferredDeliveryDate(request.getPreferredDeliveryDate())
                .recipientName(request.getRecipientName())
                .recipientPhone(request.getRecipientPhone())
                .postalCode(request.getPostalCode())
                .defaultAddress(request.getDefaultAddress())
                .extraAddress(request.getExtraAddress())
                .detailAddress(request.getDetailAddress())
                .shippingFee(request.getShippingFee())
                .shippingCode(request.getShippingCode())
                .build();
        return ShipmentResponse.from(shipmentRepository.save(shipment));
    }

    @Transactional(readOnly = true)
    public PageResponse<ShipmentResponse> getShipments(Pageable pageable) {
        Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC , "id");
        Page<Shipment> shipmentPage = shipmentRepository.findAll(paging);
        Page<ShipmentResponse> shipmentResponsePage = shipmentPage.map(ShipmentResponse::from);
        return PageResponse.from(shipmentResponsePage);
    }

    // 특정 주문에 대한 배송 정보 조회
    @Transactional(readOnly = true)
    public ShipmentResponse getShipment(long orderId) {
        Shipment shipment = getShipmentOrThrow(orderId);
        return ShipmentResponse.from(shipment);
    }

    // 주문 상태에 따른 배송 list
    @Transactional(readOnly = true)
    public PageResponse<ShipmentResponse> getShipmentListByOrderStatus(long userId, OrderStatus status, Pageable pageable){
        Page<Shipment> shipmentPage = shipmentRepository.findAllByOrderUserIdAndOrderStatus(userId, status, pageable);
        Page<ShipmentResponse> shipmentResponsePage = shipmentPage.map(ShipmentResponse::from);
        return PageResponse.from(shipmentResponsePage);
    }

    @Transactional(readOnly = true)
    public List<ShipmentResponse> getShipmentByOrderStatus(OrderStatus status) {
        List<Shipment> shipmentList = shipmentRepository.findAllByOrderStatus(status);
        return shipmentList.stream().map(ShipmentResponse::from).collect(Collectors.toList());
    }

    // 수정
    public ShipmentResponse updateShipment(long orderId, ShipmentUpdateRequest request) {
        Shipment shipment = getShipmentOrThrow(orderId);
        shipment.update(request);
        return ShipmentResponse.from(shipmentRepository.save(shipment));
    }

    // 삭제
    public void deleteShipment(long orderId) {
        Shipment shipment = getShipmentOrThrow(orderId);
        shipmentRepository.deleteById(shipment.getId());
    }

    // 배달 완료 시간 변경
    public ShipmentResponse updateShipmentDeliveredAt(long orderId, LocalDateTime deliveredAt) {
        Shipment shipment = getShipmentOrThrow(orderId);
        shipment.updateDeliveredAt(deliveredAt);
        return ShipmentResponse.from(shipmentRepository.save(shipment));
    }

    // 조회 로직
    protected Shipment getShipmentOrThrow(long orderId) {
        Optional<Shipment> optionalShipping = shipmentRepository.findByOrderId(orderId);
        if (optionalShipping.isEmpty()) {
            throw new ShipmentNotFoundException();
        }
        return optionalShipping.get();
    }
}
