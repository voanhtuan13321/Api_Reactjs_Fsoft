package fa.edu.api.services;

import fa.edu.api.entities.Order;

import java.util.List;

/**
 * Order service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
public interface OrderService {
  List<Order> findAllIsConfirmed(boolean isConfirmed);

  List<Order> findAllByUserIdAndIsConfirmed(Long userId, boolean isConfirmed);

  boolean createNewOrder(Long userId);

  void updateConfirmOrder(Long idOrder);
}
