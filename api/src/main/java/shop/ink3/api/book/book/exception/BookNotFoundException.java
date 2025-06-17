package shop.ink3.api.book.book.exception;

import shop.ink3.api.common.exception.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(Long bookId) {
        super("Book not found. ID: %d".formatted(bookId));
    }
}
