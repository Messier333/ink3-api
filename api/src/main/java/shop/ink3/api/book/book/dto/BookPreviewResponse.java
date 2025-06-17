package shop.ink3.api.book.book.dto;

import java.util.List;
import shop.ink3.api.book.book.entity.Book;

public record BookPreviewResponse(
        Long id,
        String title,
        Integer originalPrice,
        Integer salePrice,
        Integer discountRate,
        String thumbnailUrl,
        List<String> authors,
        Double averageRating,
        Long reviewCount,
        Long likeCount
) {
    public static BookPreviewResponse from(
            Book book,
            String thumbnailUrl,
            List<String> authors
    ) {
        return new BookPreviewResponse(
                book.getId(),
                book.getTitle(),
                book.getOriginalPrice(),
                book.getSalePrice(),
                book.getDiscountRate(),
                thumbnailUrl,
                authors,
                book.getAverageRating(),
                book.getReviewCount(),
                book.getLikeCount()
        );
    }
}
