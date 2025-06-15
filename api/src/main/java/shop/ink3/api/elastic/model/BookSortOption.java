package shop.ink3.api.elastic.model;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSortOption {
    POPULARITY("인기도", "popularityScore", SortOrder.Desc),
    NEWEST("신상품", "publishedAt", SortOrder.Desc),
    LOWEST_PRICE("최저가", "price", SortOrder.Asc),
    HIGHEST_PRICE("최고가", "price", SortOrder.Desc),
    RATING("평점순", "rating", SortOrder.Desc),
    REVIEW("리뷰순", "reviewCount", SortOrder.Desc);

    private final String description;
    private final String sortField;
    private final SortOrder sortOrder;
}
