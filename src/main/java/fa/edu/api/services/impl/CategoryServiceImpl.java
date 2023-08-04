package fa.edu.api.services.impl;

import fa.edu.api.entities.Book;
import fa.edu.api.entities.Cart;
import fa.edu.api.entities.Category;
import fa.edu.api.repositories.BookRepository;
import fa.edu.api.repositories.CartRepository;
import fa.edu.api.repositories.CategoryRepository;
import fa.edu.api.requests.CategoryForm;
import fa.edu.api.services.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 01/08/2023
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final BookRepository bookRepository;
  private final CartRepository cartRepository;

  /**
   * Get all categories.
   *
   * @return categories list
   */
  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  /**
   * Get category by id.
   *
   * @param idCategory need to get
   * @return category
   */
  @Override
  public Category getById(Long idCategory) {
    return categoryRepository.findById(idCategory).orElse(null);
  }

  /**
   * Add new category.
   *
   * @param categoryForm the information for category
   * @return category added
   */
  @Override
  public Category addNewCategory(CategoryForm categoryForm) {
    Category category = Category.builder()
        .categoryName(categoryForm.getCategoryName())
        .build();
    return categoryRepository.save(category);
  }

  /**
   * Update category.
   *
   * @param categoryForm information of category
   * @return category updated
   */
  @Override
  public Category updateCategory(CategoryForm categoryForm) {
    Optional<Category> optionalCategory = categoryRepository.findById(categoryForm.getCategoryId());
    if (optionalCategory.isEmpty()) {
      return null;
    }
    Category oldCategory = optionalCategory.get();

    // check if category hasn't changed name
    if (oldCategory.getCategoryName().equals(categoryForm.getCategoryName())) {
      return oldCategory;
    }

    oldCategory.setCategoryName(categoryForm.getCategoryName());
    return categoryRepository.save(oldCategory);
  }

  /**
   * Delete category.
   *
   * @param idCategory need to delete
   * @return status deleted
   */
  @Override
  @Transactional
  public boolean deleteCategory(Long idCategory) {
    Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
    if (optionalCategory.isEmpty()) {
      return false;
    }

    Category category = optionalCategory.get();
    List<Book> books = bookRepository.findAllByCategory(category);
    List<Cart> carts = new ArrayList<>();
    books.forEach(book -> {
      Optional<Cart> optionalCart = cartRepository.findByBook(book);
      optionalCart.ifPresent(carts::add);
    });

    // delete cart and book
    carts.forEach(cartRepository::delete);
    books.forEach(bookRepository::delete);

    categoryRepository.delete(category);
    return true;
  }
}
