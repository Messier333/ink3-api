package shop.ink3.api.elastic.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.ink3.api.book.book.dto.BookAuthorDto;
import shop.ink3.api.book.book.dto.BookDetailResponse;
import shop.ink3.api.book.category.dto.CategoryFlatDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookDocument {
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private List<String> authors;
    private String publisher;
    private LocalDate publishedAt;
    private List<String> categories;
    private List<String> tags;
    private Integer price;
    private Float rating;
    private Integer ratingCount;
    private Integer reviewCount;
    private Integer viewCount;
    private Integer searchCount;
    private Integer popularityScore;

    public BookDocument(BookDetailResponse bookDetailResponse) {
        this.id = bookDetailResponse.id();
        this.isbn = bookDetailResponse.isbn();
        this.title = bookDetailResponse.title();
        this.description = bookDetailResponse.description();
        this.authors = bookDetailResponse.authors().stream().map(BookAuthorDto::name).toList();
        this.publisher = bookDetailResponse.publisherName();
        this.publishedAt = bookDetailResponse.publishedAt();
        this.categories = bookDetailResponse.categories().stream()
                .flatMap(innerList -> innerList.stream().map(CategoryFlatDto::name))
                .toList();
        this.tags = bookDetailResponse.tags();
        this.price = bookDetailResponse.salePrice();
        this.rating = 0F;
        this.ratingCount = 0;
        this.reviewCount = 0;
        this.viewCount = 0;
        this.searchCount = 0;
        this.popularityScore = 0;
    }

    public void updateViewCount(int amount) {
        this.viewCount += amount;
        this.popularityScore += amount;
    }

    public void updateSearchCount(int amount) {
        this.searchCount += amount;
        this.popularityScore += amount;
    }
}
