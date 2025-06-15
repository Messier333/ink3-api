package shop.ink3.api.book.category.controller;

class CategoryControllerTest {
//
//    private CategoryRepository categoryRepository;
//    private CategoryService categoryService;
//
//    @BeforeEach
//    void setUp() {
//        categoryRepository = mock(CategoryRepository.class);
//        categoryService = new CategoryService(categoryRepository);
//    }
//
//    @Test
//    void createCategory_shouldCreateNewCategory() {
//        CategoryCreateRequest request = new CategoryCreateRequest("컴퓨터", null);
//        Category category = Category.builder().id(1L).name("컴퓨터").build();
//
//        when(categoryRepository.existsByName("컴퓨터")).thenReturn(false);
//        when(categoryRepository.save(any())).thenReturn(category);
//
//        var response = categoryService.createCategory(request);
//
//        assertThat(response.name()).isEqualTo("컴퓨터");
//        verify(categoryRepository).save(any());
//    }
//
//    @Test
//    void createCategory_whenNameExists_shouldThrowException() {
//        when(categoryRepository.existsByName("컴퓨터")).thenReturn(true);
//
//        CategoryCreateRequest request = new CategoryCreateRequest("컴퓨터", null);
//
//        assertThatThrownBy(() -> categoryService.createCategory(request))
//                .isInstanceOf(CategoryAlreadyExistsException.class);
//    }
//
//    @Test
//    void updateCategory_shouldUpdateSuccessfully() {
//        Category existing = Category.builder().id(1L).name("컴퓨터").build();
//        CategoryUpdateRequest request = new CategoryUpdateRequest("프로그래밍", null);
//
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
//
//        var result = categoryService.updateCategory(1L, request);
//
//        assertThat(result.name()).isEqualTo("프로그래밍");
//    }
//
//    @Test
//    void updateCategory_whenNotFound_shouldThrowException() {
//        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
//
//        CategoryUpdateRequest request = new CategoryUpdateRequest("프로그래밍", null);
//
//        assertThatThrownBy(() -> categoryService.updateCategory(1L, request))
//                .isInstanceOf(CategoryNotFoundException.class);
//    }
//
//    @Test
//    void deleteCategory_shouldDeleteSuccessfully() {
//        Category category = Category.builder().id(1L).name("컴퓨터").build();
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//
//        categoryService.deleteCategory(1L);
//        verify(categoryRepository).delete(category);
//    }
//
//    @Test
//    void deleteCategory_whenNotFound_shouldThrowException() {
//        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
//                .isInstanceOf(CategoryNotFoundException.class);
//    }
//
//    @Test
//    void getCategoryById_shouldReturnCategory() {
//        Category category = Category.builder().id(1L).name("컴퓨터").build();
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//
//        var result = categoryService.getCategoryById(1L);
//        assertThat(result.name()).isEqualTo("컴퓨터");
//    }
//
//    @Test
//    void getCategoryTree_shouldBuildTree() {
//        Category parent = Category.builder().id(1L).name("컴퓨터").build();
//        Category child = Category.builder().id(2L).name("프로그래밍").parent(parent).build();
//
//        when(categoryRepository.findAll()).thenReturn(List.of(parent, child));
//
//        var tree = categoryService.getCategoryTree();
//        assertThat(tree).hasSize(1);
//        assertThat(tree.get(0).children()).hasSize(1);
//        assertThat(tree.get(0).children().get(0).name()).isEqualTo("프로그래밍");
//    }
//
//    @Test
//    void updateCategory_shouldUpdateWithParent() {
//        Category parent = Category.builder().id(99L).name("개발").build();
//        Category existing = Category.builder().id(1L).name("컴퓨터").build();
//
//        CategoryUpdateRequest request = new CategoryUpdateRequest("프로그래밍", 99L);
//
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(categoryRepository.findById(99L)).thenReturn(Optional.of(parent));
//
//        var result = categoryService.updateCategory(1L, request);
//
//        assertThat(result.name()).isEqualTo("프로그래밍");
//        assertThat(result.parentId()).isEqualTo(99L);
//    }
//
//    @Test
//    void updateCategory_whenParentIdNotFound_shouldThrowException() {
//        Category existing = Category.builder().id(1L).name("컴퓨터").build();
//
//        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
//        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
//
//        CategoryUpdateRequest request = new CategoryUpdateRequest("프로그래밍", 99L);
//
//        assertThatThrownBy(() -> categoryService.updateCategory(1L, new CategoryUpdateRequest("프로그래밍", 99L)))
//                .isInstanceOf(CategoryNotFoundException.class);
//    }
//
//    @Test
//    void createCategory_whenParentIdNotFound_shouldThrowException() {
//        CategoryCreateRequest request = new CategoryCreateRequest("프론트엔드", 99L);
//
//        when(categoryRepository.existsByName("프론트엔드")).thenReturn(false);
//        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> categoryService.createCategory(request))
//                .isInstanceOf(CategoryNotFoundException.class);
//    }
}
