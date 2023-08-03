package fa.edu.api.controllers;

import fa.edu.api.entities.Admin;
import fa.edu.api.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Admin controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/admin")
@Slf4j
public class AdminController {
  @Autowired
  private AdminService adminService;

  /**
   * Check user has exited in db.
   *
   * @param username want to check
   * @return status check
   */
  @GetMapping(path = "/{username}")
  public ResponseEntity<Boolean> checkUserExists(@PathVariable(name = "username") String username) {
    log.info("Checking username has exited");
    boolean status = adminService.checkUserExists(username);
    return ResponseEntity.ok().body(status);
  }/**
   * Check login.
   *
   * @param admin want to check
   * @return status check
   */

  @PostMapping(path = "/check")
  public ResponseEntity<Long> checkLogin(@RequestBody Admin admin) {
    log.info("Checking login");
    Long idAdmin = adminService.checkLogin(admin);
    return ResponseEntity.ok().body(idAdmin);
  }
}
