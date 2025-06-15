package shop.ink3.api.book.bookCategory.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ink3.api.book.bookCategory.entity.BookCategory;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    @EntityGraph(attributePaths = "category")
    List<BookCategory> findAllByBookId(long bookId);

    void deleteAllByBookId(long bookId);
}
