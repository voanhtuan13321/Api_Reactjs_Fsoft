package fa.edu.api.repositories;

import fa.edu.api.entities.Book;
import fa.edu.api.entities.Cart;
import fa.edu.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
