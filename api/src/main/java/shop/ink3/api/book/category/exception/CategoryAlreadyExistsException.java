package shop.ink3.api.book.category.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String name) {
        super("해당 이름을 가진 카테고리가 이미 존재합니다. NAME: %s".formatted(name));
    }
}
