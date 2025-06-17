package shop.ink3.api.user.point.policy.dto;

public record PointPolicyCreateRequest(
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer imageReviewPoint,
        Integer defaultRate
) {
}
