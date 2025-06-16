package shop.ink3.api.coupon.store.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.ink3.api.coupon.store.entity.CouponStatus;
import shop.ink3.api.coupon.store.entity.CouponStore;
import shop.ink3.api.coupon.store.entity.OriginType;

public interface CouponStoreRepository extends JpaRepository<CouponStore, Long>, CouponStoreQuerydslRepository {

    @EntityGraph(attributePaths = {"coupon", "user"})
    List<CouponStore> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"coupon", "user"})
    List<CouponStore> findByCouponId(Long couponId);

    @EntityGraph(attributePaths = {"coupon", "user"})
    List<CouponStore> findByUserIdAndStatus(Long userId, CouponStatus status);
    @EntityGraph(attributePaths = {"coupon"})
    List<CouponStore> findAllByCouponIdAndStatus(Long couponId, CouponStatus status);
    boolean existsByUserIdAndOriginType(Long userId, OriginType originType);

    boolean existsByUserIdAndCouponIdAndOriginTypeAndOriginId(Long userId, Long couponId, OriginType originType,
                                                              Long originId);
    @Query("""
            SELECT cs
            FROM CouponStore cs
            JOIN FETCH cs.coupon c
            WHERE cs.user.id = :userId
              AND cs.originType = :originType
              AND cs.originId IN :originIds
              AND cs.status = :status
        """)
    List<CouponStore> findWithCouponByUserAndOriginAndStatus(
            @Param("userId") Long userId,
            @Param("originType") OriginType originType,
            @Param("originIds") Collection<Long> originIds,
            @Param("status") CouponStatus status
    );

    @Query("""
            SELECT cs
            FROM CouponStore cs
            JOIN FETCH cs.coupon c
            WHERE cs.user.id = :userId
              AND cs.originType = :originType
              AND cs.status = :status
        """)
    List<CouponStore> findWithCouponByUserAndOriginAndStatus(
            @Param("userId") Long userId,
            @Param("originType") OriginType originType,
            @Param("status") CouponStatus status
    );


    boolean existsByCouponId(Long couponId);

    boolean existsByOriginIdAndUserId(Long originId, Long userId);

    boolean existsByStatusAndUserIdAndOriginType(CouponStatus status, Long userId, OriginType originType);
}
