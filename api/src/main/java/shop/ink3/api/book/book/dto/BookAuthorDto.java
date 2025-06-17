package shop.ink3.api.book.book.dto;

import jakarta.validation.constraints.NotBlank;

public record BookAuthorDto(
        @NotBlank String name,
        @NotBlank String role
) {
}
