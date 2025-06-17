package shop.ink3.api.order.shipment.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.order.order.entity.OrderStatus;
import shop.ink3.api.order.shipment.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Page<Shipment> findAll(Pageable pageable);

    Optional<Shipment> findByOrderId(long orderId);

    Page<Shipment> findAllByOrderUserIdAndOrderStatus(long userId,OrderStatus status, Pageable pageable);

    List<Shipment> findAllByOrderStatus(OrderStatus status);
}
