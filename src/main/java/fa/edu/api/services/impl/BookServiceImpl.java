package fa.edu.api.services.impl;

import fa.edu.api.common.ImageFile;
import fa.edu.api.entities.Book;
import fa.edu.api.entities.Category;
import fa.edu.api.repositories.BookRepository;
import fa.edu.api.repositories.CategoryRepository;
import fa.edu.api.requests.BookForm;
import fa.edu.api.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Book service implement class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;

  /**
   * Get all the books.
   *
   * @return book list
   */
  @Override
  public List<Book> findAll(String searchKey) {
    List<Book> list = new ArrayList<>();
    List<Book> books = bookRepository.findAllAndSearch(searchKey.trim());

    for (Book book : books) {
      String image = book.getImageName();
      book.setImageName(ImageFile.URL_API_IMAGE + image);
      list.add(book);
    }

    return list;
  }

  @Override
  public List<Book> findAllByCategoryId(Long idCategory, String searchKey) {
    List<Book> list = new ArrayList<>();
    List<Book> bookList = bookRepository.findAllByCategoryAndSearch(idCategory, searchKey.trim());

    for (Book book : bookList) {
      String image = book.getImageName();
      book.setImageName(ImageFile.URL_API_IMAGE + image);
      list.add(book);
    }

    return list;
  }

  /**
   * Get book by id.
   *
   * @return book
   */
  @Override
  public Book findById(Long bookId) {
    Book book = bookRepository.findById(bookId).orElse(null);

    if (book != null) {
      String image = book.getImageName();
      book.setImageName(ImageFile.URL_API_IMAGE + image);
    }

    return book;
  }

  /**
   * Add new book.
   *
   * @return book
   */
  @Override
  public Book addBook(BookForm bookForm) {
    Category category = categoryRepository.findById(bookForm.getCategoryId()).orElse(null);

    if (category == null) {
      return null;
    }

    try {
      String fileName = ImageFile.saveImageFile(bookForm.getImage());
      if (fileName.isEmpty()) {
        return null;
      }

      Book newBook = Book.builder()
          .category(category)
          .title(bookForm.getTitle())
          .description(bookForm.getDescription())
          .author(bookForm.getAuthor())
          .imageName(fileName)
          .price(bookForm.getPrice())
          .quantity(bookForm.getQuantity())
          .build();

      return bookRepository.save(newBook);
    } catch (IOException e) {
      log.error("save file error", e);
      return null;
    }

  }

  /**
   * Update Book.
   *
   * @param bookForm information about the book
   * @return book
   */
  @Override
  public Book updateBook(BookForm bookForm) {
    Optional<Book> optionalBook = bookRepository.findById(bookForm.getBookId());

    if (optionalBook.isEmpty()) {
      return null;
    }

    Book oldBook = optionalBook.get();

    // check if the category has changed
    if (!Objects.equals(oldBook.getCategory().getCategoryId(), bookForm.getCategoryId())) {
      Category newCategory = categoryRepository.findById(bookForm.getCategoryId()).orElse(null);
      oldBook.setCategory(newCategory);
    }

    // check if the image has changed
    if (bookForm.getImage() != null) {
      String fileName = null;
      try {
        fileName = ImageFile.saveImageFile(bookForm.getImage());
      } catch (IOException e) {
        log.error("save file error", e);
        return null;
      }
      boolean hasRemoved = ImageFile.deleteImageFile(oldBook.getImageName());

      if (fileName.isEmpty() && hasRemoved) {
        return null;
      }
      oldBook.setImageName(fileName);
    }

    oldBook.setTitle(bookForm.getTitle());
    oldBook.setDescription(bookForm.getDescription());
    oldBook.setAuthor(bookForm.getAuthor());
    oldBook.setPrice(bookForm.getPrice());
    oldBook.setQuantity(bookForm.getQuantity());

    return bookRepository.save(oldBook);
  }

  /**
   * Delete Book.
   *
   * @param bookId need to delete
   * @return status delete
   */
  @Override
  public boolean deleteBook(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);

    if (optionalBook.isEmpty()) {
      return false;
    }
    Book book = optionalBook.get();

    ImageFile.deleteImageFile(book.getImageName());

    bookRepository.delete(book);
    return true;
  }

  /**
   * Top good price book.
   *
   * @param topBook the top price book
   * @return list of books
   */
  @Override
  public List<Book> topGoodPriceBook(int topBook) {
    Sort sort = Sort.by("price").ascending();
    return bookRepository.findFirst5By(sort);
  }
}
