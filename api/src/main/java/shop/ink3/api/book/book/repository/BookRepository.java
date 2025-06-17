package shop.ink3.api.book.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.ink3.api.book.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>, BookQuerydslRepository {

    boolean existsByIsbn(String isbn);

    @Query("""
                SELECT b
                FROM Book b
                JOIN OrderBook ob ON ob.book = b
                JOIN Order o ON ob.order = o
                WHERE o.status = 'DELIVERED'
                GROUP BY b.id
                ORDER BY SUM(ob.quantity) DESC
            """)
    Page<Book> findBestSellerBooks(Pageable pageable);

    Page<Book> findAllByOrderByPublishedAtDesc(Pageable pageable);

    @Query("""
                SELECT b
                FROM Book b
                JOIN Like l ON l.book = b
                GROUP BY b.id
                ORDER BY COUNT(l.id) DESC
            """)
    Page<Book> findRecommendedBooks(Pageable pageable);
}
