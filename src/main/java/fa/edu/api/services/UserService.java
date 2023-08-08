package fa.edu.api.services;

import fa.edu.api.entities.User;

/**
 * User service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface UserService {
  boolean checkExitsUsername(String username);

  boolean register(User user);

  Long checkLogin(User user);

  boolean updateInfoUser(User user);

  User getInfoUser(Long id);
}
