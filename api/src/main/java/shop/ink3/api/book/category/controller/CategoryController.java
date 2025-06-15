package shop.ink3.api.book.category.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ink3.api.book.category.dto.CategoryChangeParentRequest;
import shop.ink3.api.book.category.dto.CategoryCreateRequest;
import shop.ink3.api.book.category.dto.CategoryFlatDto;
import shop.ink3.api.book.category.dto.CategoryTreeDto;
import shop.ink3.api.book.category.dto.CategoryUpdateNameRequest;
import shop.ink3.api.book.category.service.CategoryService;
import shop.ink3.api.common.dto.CommonResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/tree")
    public ResponseEntity<CommonResponse<List<CategoryTreeDto>>> getAllCategoriesTree() {
        return ResponseEntity.ok(CommonResponse.success(categoryService.getCategoriesTree()));
    }

    @GetMapping("/flat")
    public ResponseEntity<CommonResponse<List<CategoryFlatDto>>> getAllCategoriesFlat() {
        return ResponseEntity.ok(CommonResponse.success(categoryService.getCategoriesFlat()));
    }

    @GetMapping("/{id}/descendants")
    public ResponseEntity<CommonResponse<CategoryTreeDto>> getAllDescendants(@PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.success(categoryService.getAllDescendants(id)));
    }

    @GetMapping("/{id}/ancestor")
    public ResponseEntity<CommonResponse<List<CategoryFlatDto>>> getAllAncestors(@PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.success(categoryService.getAllAncestors(id)));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CategoryTreeDto>> createCategory(
            @RequestBody @Valid CategoryCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.create(categoryService.createCategory(request)));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateName(
            @PathVariable long id,
            @RequestBody @Valid CategoryUpdateNameRequest request
    ) {
        categoryService.updateCategoryName(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/parent")
    public ResponseEntity<Void> changeParent(@PathVariable long id,
                                             @RequestBody @Valid CategoryChangeParentRequest request) {
        categoryService.changeParent(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
