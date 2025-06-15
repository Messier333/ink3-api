package shop.ink3.api.book.category.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryChangeParentRequest(
        @NotNull Long parentId
) {
}
