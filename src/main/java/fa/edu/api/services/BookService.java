package fa.edu.api.services;

import fa.edu.api.entities.Book;
import fa.edu.api.requests.BookForm;

import java.util.List;

/**
 * Book service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
public interface BookService {
  List<Book> findAll(String searchKey);

  List<Book> findAllByCategoryId(Long idCategory, String searchKey);

  Book findById(Long bookId);

  Book addBook(BookForm bookForm);

  Book updateBook(BookForm bookForm);

  boolean deleteBook(Long bookId);
}
