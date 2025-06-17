package shop.ink3.api.coupon.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.ink3.api.coupon.store.entity.CouponStatus;
import shop.ink3.api.coupon.store.entity.CouponStore;

public interface CouponStoreQuerydslRepository {
    Page<CouponStore> findStoresByUserId(Long userId, CouponStatus status, Pageable pageable);

    Page<CouponStore> findStoresByUserId(Long userId, List<CouponStatus> statuses, Pageable pageable);
}
