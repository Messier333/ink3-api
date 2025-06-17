package shop.ink3.api.user.point.policy.dto;

import java.time.LocalDateTime;
import shop.ink3.api.user.point.policy.entity.PointPolicy;

public record PointPolicyResponse(
        Long id,
        String name,
        Integer joinPoint,
        Integer reviewPoint,
        Integer imageReviewPoint,
        Integer defaultRate,
        Boolean isActive,
        LocalDateTime createdAt
) {
    public static PointPolicyResponse from(PointPolicy pointPolicy) {
        return new PointPolicyResponse(
                pointPolicy.getId(),
                pointPolicy.getName(),
                pointPolicy.getJoinPoint(),
                pointPolicy.getReviewPoint(),
                pointPolicy.getImageReviewPoint(),
                pointPolicy.getDefaultRate(),
                pointPolicy.getIsActive(),
                pointPolicy.getCreatedAt()
        );
    }
}
