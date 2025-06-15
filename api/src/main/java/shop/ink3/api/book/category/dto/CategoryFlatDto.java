package shop.ink3.api.book.category.dto;

import shop.ink3.api.book.category.entity.Category;

public record CategoryFlatDto(
        Long id,
        String name,
        Long parentId,
        Integer depth
) {
    public static CategoryFlatDto from(Category category) {
        return new CategoryFlatDto(
                category.getId(),
                category.getName(),
                category.getParent() == null ? null : category.getParent().getId(),
                category.getDepth()
        );
    }
}
