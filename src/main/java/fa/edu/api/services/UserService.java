package fa.edu.api.services;

import fa.edu.api.entities.User;
import fa.edu.api.requests.Response1Form;

import java.util.List;

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

  List<Response1Form> topUserBuyTheMost(int top);
}
