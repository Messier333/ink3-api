package shop.ink3.api.book.tag.repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.book.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(@NotNull @Length(max = 20) String tagName);

    boolean existsByName(@NotNull @Length(max = 20) String name);

    List<Tag> findAllByNameIn(List<String> names);
}
