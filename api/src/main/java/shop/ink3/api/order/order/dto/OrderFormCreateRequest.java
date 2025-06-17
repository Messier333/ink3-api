package shop.ink3.api.order.order.dto;

import java.util.List;
import shop.ink3.api.order.orderBook.dto.OrderBookCreateRequest;
import shop.ink3.api.order.shipment.dto.ShipmentCreateRequest;
import shop.ink3.api.payment.entity.PaymentType;

public record OrderFormCreateRequest(
        OrderCreateRequest orderCreateRequest,
        ShipmentCreateRequest shipmentCreateRequest,
        List<OrderBookCreateRequest> createRequestList,
        int discountAmount,
        int usedPointAmount,
        int paymentAmount,
        PaymentType paymentType
) {
}
