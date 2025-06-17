package shop.ink3.api.user.point.policy.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.user.point.policy.entity.PointPolicy;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long>, PointPolicyQuerydslRepository {
    Optional<PointPolicy> findByIsActive(boolean isActive);

    boolean existsByIsActive(Boolean isActive);
}
