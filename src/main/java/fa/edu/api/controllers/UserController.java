package fa.edu.api.controllers;

import fa.edu.api.entities.User;
import fa.edu.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/users")
@Slf4j
public class UserController {
  @Autowired
  private UserService userService;

  /**
   * Check username has existed.
   *
   * @param username want to check
   * @return status
   */
  @GetMapping(path = "/{username}")
  public ResponseEntity<Boolean> checkExitsUsername(@PathVariable(name = "username") String username) {
    log.info("Checking exist username");
    boolean hasExits = userService.checkExitsUsername(username);
    return ResponseEntity.ok().body(hasExits);
  }

  /**
   * Registers a new user.
   *
   * @param user want to register
   * @return status
   */
  @PostMapping(path = "/register")
  public ResponseEntity<Boolean> registerUser(@RequestBody User user) {
    log.info("Register user");
    boolean status = userService.register(user);
    return ResponseEntity.ok().body(status);
  }

  /**
   * Check the user.
   *
   * @param user want to check
   * @return id of the user
   */
  @PostMapping(path = "/login")
  public ResponseEntity<Long> checkLoginUser(@RequestBody User user) {
    log.info("Check login user");
    Long idUser = userService.checkLogin(user);
    return ResponseEntity.ok().body(idUser);
  }

  /**
   * Update information of a user.
   *
   * @param user want to update
   * @return status
   */
  @PutMapping(path = "/edit")
  public ResponseEntity<Boolean> updateInfoUser(@RequestBody User user) {
    log.info("update info user");
    boolean status = userService.updateInfoUser(user);
    return ResponseEntity.ok().body(status);
  }
}
