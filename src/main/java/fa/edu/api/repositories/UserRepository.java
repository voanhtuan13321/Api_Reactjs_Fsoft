package fa.edu.api.repositories;

import fa.edu.api.common.QueryString;
import fa.edu.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
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

  @Query(value = QueryString.TOP_USER_BUY_THE_MOST, nativeQuery = true)
  List<Map<String, Long>> topUserBuyTheMost(int top);
}
