package shop.ink3.api.user.point.policy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "point_policies")
public class PointPolicy {

    @Builder
    public PointPolicy(String name, int joinPoint, int reviewPoint, int imageReviewPoint, int defaultRate) {
        this.name = name;
        this.joinPoint = joinPoint;
        this.reviewPoint = reviewPoint;
        this.imageReviewPoint = imageReviewPoint;
        this.defaultRate = defaultRate;
        this.isActive = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Integer joinPoint;

    @Column(nullable = false)
    private Integer reviewPoint;

    @Column(nullable = false)
    private Integer imageReviewPoint;

    @Column(nullable = false)
    private Integer defaultRate;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void update(String name, int joinPoint, int reviewPoint, int imageReviewPoint, int defaultRate) {
        this.name = name;
        this.joinPoint = joinPoint;
        this.reviewPoint = reviewPoint;
        this.imageReviewPoint = imageReviewPoint;
        this.defaultRate = defaultRate;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
