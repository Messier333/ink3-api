package shop.ink3.api.book.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long categoryId) {
        super("카테고리를 찾을 수 없습니다. ID: %d".formatted(categoryId));
    }

    public CategoryNotFoundException(String categoryName) {
        super("카테고리를 찾을 수 없습니다. NAME: %s".formatted(categoryName));
    }
}
