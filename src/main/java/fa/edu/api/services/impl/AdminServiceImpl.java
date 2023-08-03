package fa.edu.api.services.impl;

import fa.edu.api.entities.Admin;
import fa.edu.api.repositories.AdminRepository;
import fa.edu.api.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Admin service implement class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@Service
public class AdminServiceImpl implements AdminService {
  @Autowired
  private AdminRepository adminRepository;

  /**
   * Check user has exited in db.
   *
   * @param username want to check
   * @return status check
   */
  @Override
  public boolean checkUserExists(String username) {
    int count = adminRepository.countByEmail(username);
    return count > 0;
  }

  /**
   * Check login.
   *
   * @param admin want to check
   * @return status check
   */
  @Override
  public Long checkLogin(Admin admin) {
    Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
    return optionalAdmin.map(Admin::getAdminId).orElse(null);
  }
}
