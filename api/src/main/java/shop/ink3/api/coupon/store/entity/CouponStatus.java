package shop.ink3.api.coupon.store.entity;

public enum CouponStatus {
    READY,     // 발급만 된 상태
    USED,      // 실제 사용됨
    EXPIRED ,   // 유효기간 지남
    DISABLED // 관리자가 사용 불가 처리
}
