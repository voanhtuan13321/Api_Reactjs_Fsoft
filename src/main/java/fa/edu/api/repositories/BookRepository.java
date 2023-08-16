package fa.edu.api.repositories;

import fa.edu.api.common.QueryString;
import fa.edu.api.entities.Book;
import fa.edu.api.entities.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
  List<Book> findAllAndSearch(
      @Param("search") String searchKey,
      @Param("numPage") int numPage,
      @Param("numRowInPage") int numRowInPage
  );

  @Query(value = QueryString.FIND_ALL_BY_CATEGORY_SEARCH, nativeQuery = true)
  List<Book> findAllByCategoryAndSearch(
      @Param("category_id") Long category,
      @Param("search") String searchKey
  );

  List<Book> findAllByCategory(Category category);

  List<Book> findFirst5By(Sort sort);

  @Query(value = QueryString.COUNT_ALL_BOOK, nativeQuery = true)
  long countAllBooksBySearch(@Param("search") String searchKey);

  @Query(value = QueryString.COUNT_ALL_BOOK_BY_CATEGORY, nativeQuery = true)
  long countAllBooksBySearch(
      @Param("search") String searchKey,
      @Param("category_id") int categoryId
  );
}
