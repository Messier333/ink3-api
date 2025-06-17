package shop.ink3.api.book.book.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import shop.ink3.api.book.book.entity.BookStatus;
import shop.ink3.api.book.book.external.aladin.dto.AladinBookResponse;

public record BookRegisterRequest(
        AladinBookResponse aladinBookResponse,
        String contents,
        @NotNull @PositiveOrZero Integer priceSales,
        @NotNull @PositiveOrZero Integer quantity,
        @NotNull BookStatus status,
        Boolean isPackable,
        List<String> tags
) {
}
