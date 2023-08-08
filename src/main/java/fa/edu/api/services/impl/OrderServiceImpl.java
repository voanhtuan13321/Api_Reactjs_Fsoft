package fa.edu.api.services.impl;

import fa.edu.api.entities.Cart;
import fa.edu.api.entities.Order;
import fa.edu.api.entities.OrderDetail;
import fa.edu.api.entities.User;
import fa.edu.api.repositories.CartRepository;
import fa.edu.api.repositories.OrderDetailRepository;
import fa.edu.api.repositories.OrderRepository;
import fa.edu.api.repositories.UserRepository;
import fa.edu.api.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Order service implement class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final OrderDetailRepository orderDetailRepository;

  /**
   * Get all the orders is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllIsConfirmed(boolean isConfirmed) {
    List<Order> list = new ArrayList<>();
    List<Order> orderList = orderRepository.findAll();

    for (Order order : orderList) {
      if (order.isConfirm() == isConfirmed) {
        order.getUser().setPassword("");
        list.add(order);
      }
    }

    return list.stream()
        .sorted(Comparator.comparing(Order::getOrderDate))
        .toList();
  }

  /**
   * Get all the orders by user id and is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllByUserIdAndIsConfirmed(Long userId, boolean isConfirmed) {
    List<Order> list = new ArrayList<>();
    List<Order> orderList = orderRepository.findAll();

    for (Order order : orderList) {
      if (order.isConfirm() == isConfirmed && Objects.equals(order.getUser().getUserId(), userId)) {
        order.getUser().setPassword("");
        list.add(order);
      }
    }

    return list.stream()
        .sorted(Comparator.comparing(Order::getOrderDate))
        .toList();
  }

  /**
   * Create new order.
   *
   * @param userId want to create
   * @return status
   */
  @Override
  public boolean createNewOrder(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalUser.isEmpty()) {
      return false;
    }

    User user = optionalUser.get();
    // add to order
    Order order = Order.builder()
        .user(user)
        .orderDate(LocalDateTime.now())
        .isConfirm(false)
        .build();
    orderRepository.save(order);

    // add to order details
    List<Cart> carts = cartRepository.findAllByUser(user);
    carts.forEach(cart ->
        orderDetailRepository.save(
            OrderDetail.builder()
                .order(order)
                .book(cart.getBook())
                .quantity(cart.getQuantity())
                .build()
        )
    );

    // remove from cart
    carts.forEach(cartRepository::delete);
    return true;
  }

  /**
   * Update confirm order.
   *
   * @param idOrder want to confirm
   */
  @Override
  public void updateConfirmOrder(Long idOrder) {
    Order order = orderRepository.findById(idOrder).orElseThrow();
    order.setConfirm(true);
    orderRepository.save(order);
  }
}
