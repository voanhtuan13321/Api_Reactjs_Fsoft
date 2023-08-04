package fa.edu.api.repositories;

import fa.edu.api.entities.Book;
import fa.edu.api.entities.Cart;
import fa.edu.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Cart repository class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
  List<Cart> findAllByUser(User user);

  Optional<Cart> findByUserAndBook(User user, Book book);

  Optional<Cart> findByBook(Book book);
}
