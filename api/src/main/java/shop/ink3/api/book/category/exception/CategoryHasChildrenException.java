package shop.ink3.api.book.category.exception;

public class CategoryHasChildrenException extends RuntimeException {
    public CategoryHasChildrenException(long categoryId) {
        super("하위 카테고리가 존재하여 삭제할 수 없습니다. ID: " + categoryId);
    }
}
