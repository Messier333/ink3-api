package shop.ink3.api.coupon.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;

import shop.ink3.api.coupon.coupon.entity.QCoupon;
import shop.ink3.api.coupon.policy.entity.QCouponPolicy;
import shop.ink3.api.coupon.store.entity.CouponStatus;
import shop.ink3.api.coupon.store.entity.CouponStore;
import shop.ink3.api.coupon.store.entity.QCouponStore;
import shop.ink3.api.user.user.entity.QUser;

public class CouponStoreQuerydslRepositoryImpl extends QuerydslRepositorySupport implements
    CouponStoreQuerydslRepository {

    public CouponStoreQuerydslRepositoryImpl() {
        super(CouponStore.class);
    }

    @Override
    public Page<CouponStore> findStoresByUserId(Long userId, CouponStatus status, Pageable pageable) {
        QCouponStore cs = QCouponStore.couponStore;
        QCoupon coupon = QCoupon.coupon;
        QCouponPolicy policy = QCouponPolicy.couponPolicy;
        QUser user = QUser.user;

        JPQLQuery<CouponStore> query = from(cs)
            .join(cs.coupon, coupon).fetchJoin()
            .join(coupon.couponPolicy, policy).fetchJoin()
            .join(cs.user, user).fetchJoin()
            .where(cs.user.id.eq(userId));

        if (status != null) {
            query.where(cs.status.eq(status));
        }

        query.orderBy(cs.issuedAt.desc());

        JPQLQuery<CouponStore> pagedQuery = getQuerydsl().applyPagination(pageable, query);

        List<CouponStore> content = pagedQuery.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<CouponStore> findStoresByUserId(Long userId, List<CouponStatus> statuses, Pageable pageable) {
        QCouponStore cs = QCouponStore.couponStore;
        QCoupon coupon = QCoupon.coupon;
        QCouponPolicy policy = QCouponPolicy.couponPolicy;
        QUser user = QUser.user;

        JPQLQuery<CouponStore> query = from(cs)
            .join(cs.coupon, coupon).fetchJoin()
            .join(coupon.couponPolicy, policy).fetchJoin()
            .join(cs.user, user).fetchJoin()
            .where(cs.user.id.eq(userId));

        if (statuses != null && !statuses.isEmpty()) {
            query.where(cs.status.in(statuses));
        }

        query.orderBy(
            Expressions.stringTemplate("case when {0} = 'READY' then 0 else 1 end", cs.status).asc(),
            cs.issuedAt.desc()
        );

        JPQLQuery<CouponStore> pagedQuery = getQuerydsl().applyPagination(pageable, query);
        List<CouponStore> content = pagedQuery.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

}
