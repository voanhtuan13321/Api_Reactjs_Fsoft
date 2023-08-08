package fa.edu.api.controllers;

import fa.edu.api.entities.Category;
import fa.edu.api.requests.CategoryForm;
import fa.edu.api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * Get all categories.
   *
   * @return categories list
   */
  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    log.info("get All Categories");
    List<Category> categories = categoryService.getAllCategories();
    return ResponseEntity.ok().body(categories);
  }

  /**
   * Get category by id.
   *
   * @param idCategory need to get
   * @return category
   */
  @GetMapping(path = "/{idCategory}")
  public ResponseEntity<Category> getCategoryById(@PathVariable(name = "idCategory") Long idCategory) {
    log.info("get Category By Id");
    Category category = categoryService.getById(idCategory);
    return ResponseEntity.ok().body(category);
  }

  /**
   * Add new category.
   *
   * @param categoryForm the information for category
   * @return category added
   */
  @PostMapping
  public ResponseEntity<Category> addNewCategory(@RequestBody CategoryForm categoryForm) {
    log.info("add new category");
    Category category = categoryService.addNewCategory(categoryForm);
    return ResponseEntity.ok().body(category);
  }

  /**
   * Update category.
   *
   * @param categoryForm information of category
   * @return category updated
   */
  @PutMapping
  public ResponseEntity<Category> updateCategory(@RequestBody CategoryForm categoryForm) {
    log.info("update category");
    Category category = categoryService.updateCategory(categoryForm);
    return ResponseEntity.ok().body(category);
  }

  /**
   * Delete category.
   *
   * @param idCategory need to delete
   * @return status deleted
   */
  @DeleteMapping(path = "{idCategory}")
  public ResponseEntity<Boolean> deleteCategory(@PathVariable(name = "idCategory") Long idCategory) {
    log.info("delete category");
    boolean status = categoryService.deleteCategory(idCategory);
    return ResponseEntity.ok().body(status);
  }

}
