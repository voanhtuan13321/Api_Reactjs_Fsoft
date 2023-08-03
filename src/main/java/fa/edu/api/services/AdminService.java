package fa.edu.api.services;

import fa.edu.api.entities.Admin;

/**
 * Admin service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface AdminService {
  boolean checkUserExists(String username);

  Long checkLogin(Admin admin);
}
