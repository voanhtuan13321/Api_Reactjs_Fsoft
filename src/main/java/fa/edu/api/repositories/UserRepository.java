package fa.edu.api.repositories;

import fa.edu.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface UserRepository extends JpaRepository<User, Long> {
  int countByEmail(String username);

  Optional<User> findByEmailAndPassword(String email, String password);
}
