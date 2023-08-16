package fa.edu.api.services.impl;

import fa.edu.api.entities.User;
import fa.edu.api.repositories.OrderRepository;
import fa.edu.api.repositories.UserRepository;
import fa.edu.api.requests.Response1Form;
import fa.edu.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpt implements UserService {

  private final UserRepository userRepository;
  private final OrderRepository orderRepository;

  /**
   * Check username has existed.
   *
   * @param username want to check
   * @return status
   */
  @Override
  public boolean checkExitsUsername(String username) {
    int count = userRepository.countByEmail(username);
    return count > 0;
  }

  /**
   * Registers a new user.
   *
   * @param user want to register
   * @return status
   */
  @Override
  public boolean register(User user) {
    // check existed username
    boolean hasExistUsername = checkExitsUsername(user.getEmail());
    if (hasExistUsername) {
      return false;
    }

    userRepository.save(user);
    return true;
  }

  /**
   * Check the user.
   *
   * @param user want to check
   * @return id of the user
   */
  @Override
  public Long checkLogin(User user) {
    Optional<User> userOptional = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    return userOptional.map(User::getUserId).orElse(null);
  }

  /**
   * Update information of a user.
   *
   * @param user want to update
   * @return status
   */
  @Override
  public boolean updateInfoUser(User user) {
    Optional<User> optionalUser = userRepository.findById(user.getUserId());

    if (optionalUser.isEmpty()) {
      return false;
    }

    User oldUser = optionalUser.get();
    oldUser.setName(user.getName());
    oldUser.setPhone(user.getPhone());
    oldUser.setAddress(user.getAddress());
    userRepository.save(oldUser);
    return true;
  }

  @Override
  public User getInfoUser(Long id) {
    User user = userRepository.findById(id).orElse(null);
    if (user != null) {
      user.setPassword("");
    }
    return user;
  }

  @Override
  public List<Response1Form> topUserBuyTheMost(int top) {
    User user = null;
    Response1Form response1Form = null;
    List<Response1Form> response1Forms = new ArrayList<>();
    List<Map<String, Long>> result = userRepository.topUserBuyTheMost(top);

    for (Map<String, Long> rs : result) {
      user = userRepository.findById(rs.get("user_id")).orElseThrow();
      user.setPassword("");

      response1Form = Response1Form.builder()
          .user(user)
          .quantity(rs.get("quantity"))
          .build();
      response1Forms.add(response1Form);
    }

    return response1Forms;
  }

}
