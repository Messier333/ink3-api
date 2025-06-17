package shop.ink3.api.book.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank String name,
        Long parentId
) {
}
