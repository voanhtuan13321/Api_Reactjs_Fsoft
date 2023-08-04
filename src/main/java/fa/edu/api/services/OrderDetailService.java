package fa.edu.api.services;

import fa.edu.api.entities.OrderDetail;

import java.util.List;

/**
 * Order detail service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
public interface OrderDetailService {
  List<OrderDetail> getAllOrderDetailByOrderId(Long orderId);
}
