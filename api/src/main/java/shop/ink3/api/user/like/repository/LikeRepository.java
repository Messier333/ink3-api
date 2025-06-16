package shop.ink3.api.user.like.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.user.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @EntityGraph(attributePaths = "book")
    Page<Like> findAllByUserId(long userId, Pageable pageable);

    boolean existsByUserIdAndBookId(long userId, long bookId);

    Optional<Like> findByIdAndUserId(long likeId, long userId);
}
