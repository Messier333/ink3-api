package shop.ink3.api.book.book.external.aladin.dto;

public record AladinBookResponse(
        String title,
        String description,
        String author,
        String publisher,
        String pubDate,
        String isbn13,
        int priceStandard,
        String cover,
        String categoryName
) {}
