package fa.edu.api.repositories;

import fa.edu.api.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Admin repository class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {
  int countByEmail(String username);

  Optional<Admin> findByEmailAndPassword(String email, String password);
}
