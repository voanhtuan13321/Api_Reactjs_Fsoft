package fa.edu.api.services;

import fa.edu.api.entities.Category;
import fa.edu.api.requests.CategoryForm;

import java.util.List;

/**
 * Category repository class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 01/08/2023
 */
public interface CategoryService {
  List<Category> getAllCategories();

  Category getById(Long idCategory);

  Category addNewCategory(CategoryForm categoryForm);

  Category updateCategory(CategoryForm categoryForm);

  boolean deleteCategory(Long idCategory);
}
