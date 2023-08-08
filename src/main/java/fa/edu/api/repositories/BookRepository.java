package fa.edu.api.repositories;

import fa.edu.api.common.QueryString;
import fa.edu.api.entities.Book;
import fa.edu.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Book repository class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
public interface BookRepository extends JpaRepository<Book, Long> {
  @Query(value = QueryString.FIND_ALL_SEARCH, nativeQuery = true)
  List<Book> findAllAndSearch(String searchKey);

  @Query(value = QueryString.FIND_ALL_BY_CATEGORY_SEARCH, nativeQuery = true)
  List<Book> findAllByCategoryAndSearch(Long category, String searchKey);

  List<Book> findAllByCategory(Category category);
}
