package shop.ink3.api.book.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import shop.ink3.api.book.book.entity.BookStatus;

public record BookCreateRequest(
        @NotBlank @Size(max = 13) String isbn,
        @NotBlank String title,
        String contents,
        @NotBlank String description,
        @NotNull @PastOrPresent LocalDate publishedAt,
        @NotNull @PositiveOrZero Integer originalPrice,
        @NotNull @PositiveOrZero Integer salePrice,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull BookStatus status,
        @NotNull Boolean isPackable,
        @NotNull String publisher,

        @NotEmpty(message = "카테고리는 최소 1개 이상이어야 합니다.")
        List<@NotNull Long> categoryIds,
        @NotEmpty(message = "저자는 최소 1명 이상이어야 합니다.")
        List<BookAuthorDto> authors,
        List<@NotBlank String> tags
) {
}
