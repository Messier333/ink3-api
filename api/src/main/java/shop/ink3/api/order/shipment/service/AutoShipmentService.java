package shop.ink3.api.order.shipment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import shop.ink3.api.order.order.dto.OrderStatusUpdateRequest;
import shop.ink3.api.order.order.entity.OrderStatus;
import shop.ink3.api.order.order.service.OrderService;
import shop.ink3.api.order.shipment.dto.ShipmentResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class AutoShipmentService {
    private final ShipmentService shipmentService;
    private final OrderService orderService;

    @Scheduled(cron = "0 0 * * * *")
    public void autoBatchToSHIPPING() {
        log.info("🚚 [스케줄러 실행] autoBatchToSHIPPING 실행됨");

        List<ShipmentResponse> shipmentByOrderStatus = shipmentService.getShipmentByOrderStatus(OrderStatus.CONFIRMED);

        for(ShipmentResponse shipmentResponse : shipmentByOrderStatus) {
            orderService.updateOrderStatus(shipmentResponse.getOrderId(), new OrderStatusUpdateRequest(OrderStatus.SHIPPING));
            shipmentService.updateShipmentDeliveredAt(shipmentResponse.getOrderId(), LocalDateTime.now());
        }
    }

    @Scheduled(cron = "0 1 0 * * *")
    public void autoBatchToDELIVERED() {
        System.out.println("✅ 스케줄러 실행됨: " + LocalDateTime.now());

        List<ShipmentResponse> shipmentByOrderStatus = shipmentService.getShipmentByOrderStatus(OrderStatus.SHIPPING);

        for(ShipmentResponse shipmentResponse : shipmentByOrderStatus) {
            if(shipmentResponse.getPreferredDeliveryDate().isEqual(LocalDate.now())){
                orderService.updateOrderStatus(shipmentResponse.getOrderId(), new OrderStatusUpdateRequest(OrderStatus.DELIVERED));
                shipmentService.updateShipmentDeliveredAt(shipmentResponse.getOrderId(), LocalDateTime.now());
            }
        }
    }
}
