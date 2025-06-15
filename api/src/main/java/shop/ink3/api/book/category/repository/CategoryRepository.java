package shop.ink3.api.book.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.book.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    boolean existsByPathStartingWith(String path);

    Optional<Category> findByName(String name);

    List<Category> findAllByPathStartsWith(String path, Sort sort);
}
