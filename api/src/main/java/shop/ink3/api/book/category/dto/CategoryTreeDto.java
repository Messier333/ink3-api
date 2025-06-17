package shop.ink3.api.book.category.dto;

import java.util.List;

public record CategoryTreeDto(
        Long id,
        String name,
        List<CategoryTreeDto> children
) {
}
