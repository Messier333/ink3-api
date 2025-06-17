package shop.ink3.api.book.author.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ink3.api.book.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    List<Author> findAllByNameIn(Iterable<String> names);
}
