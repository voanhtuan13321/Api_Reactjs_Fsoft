package fa.edu.api.services.impl;

import fa.edu.api.entities.Order;
import fa.edu.api.repositories.OrderRepository;
import fa.edu.api.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  /**
   * Get all the orders is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllIsConfirmed(boolean isConfirmed) {
    return orderRepository.findAll()
        .stream()
        .filter(order -> order.isConfirm() == isConfirmed)
        .toList();
  }

  /**
   * Get all the orders by user id and is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllByUserIdAndIsConfirmed(Long userId, boolean isConfirmed) {
    return orderRepository.findAll()
        .stream()
        .filter(order ->
            order.isConfirm() == isConfirmed && Objects.equals(order.getUser().getUserId(), userId)
        )
        .toList();
  }
}
