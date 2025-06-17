package shop.ink3.api.book.book.dto;

import java.time.LocalDate;
import shop.ink3.api.book.book.entity.Book;
import shop.ink3.api.book.book.entity.BookStatus;

public record AdminBookResponse(
        Long id,
        String isbn,
        String title,
        String publisher,
        LocalDate publishedAt,
        int originalPrice,
        int salePrice,
        int discountRate,
        int quantity,
        boolean isPackable,
        double averageRating,
        String thumbnailUrl,
        BookStatus status
) {
    public static AdminBookResponse from(Book book, String thumbnailUrl) {
        return new AdminBookResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getPublisher().getName(),
                book.getPublishedAt(),
                book.getOriginalPrice(),
                book.getSalePrice(),
                book.getDiscountRate(),
                book.getQuantity(),
                book.isPackable(),
                book.getAverageRating(),
                thumbnailUrl,
                book.getStatus()
        );
    }
}
