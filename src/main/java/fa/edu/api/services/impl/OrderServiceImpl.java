package fa.edu.api.services.impl;

import fa.edu.api.entities.*;
import fa.edu.api.repositories.*;
import fa.edu.api.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
  private final BookRepository bookRepository;

  /**
   * Get all the orders is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllIsConfirmed(boolean isConfirmed) {
    List<Order> list = new ArrayList<>();
    List<Order> orderList = orderRepository.findAllByOrderByOrderDateDesc();

    for (Order order : orderList) {
      if (order.isConfirm() == isConfirmed) {
        order.getUser().setPassword("");
        list.add(order);
      }
    }

    return list;
  }

  /**
   * Get all the orders by user id and is confirmed or is not confirmed.
   *
   * @return list of orders
   */
  @Override
  public List<Order> findAllByUserIdAndIsConfirmed(Long userId, boolean isConfirmed) {
    List<Order> list = new ArrayList<>();
    List<Order> orderList = orderRepository.findAllByOrderByOrderDateDesc();

    for (Order order : orderList) {
      if (order.isConfirm() == isConfirmed && Objects.equals(order.getUser().getUserId(), userId)) {
        order.getUser().setPassword("");
        list.add(order);
      }
    }

    return list;
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
    carts.forEach(cart -> {
      Book book = cart.getBook();
      OrderDetail newOrderDetail = OrderDetail.builder()
          .order(order)
          .book(book)
          .quantity(cart.getQuantity())
          .build();
      orderDetailRepository.save(newOrderDetail);

      // reduce quantity
      book.setQuantity(book.getQuantity() - cart.getQuantity());
      bookRepository.save(book);
    });

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

  /**
   * Statistical.
   *
   * @param month the month want to statistic
   * @param year  the year want to statistic
   * @return statistical
   */
  @Override
  public List<Double> statisticalByMonthAndYear(int month, int year) {
    int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
    List<Double> response3Forms = new ArrayList<>();
    List<Map<String, Object>> results = orderRepository.statistical(year, month);

    // create days of the month
    for (int i = 0; i < daysInMonth; i++) {
      response3Forms.add(0.0);
    }

    for (Map<String, Object> result : results) {
      Timestamp orderDate = (Timestamp) result.get("order_date");
      double total = (double) result.get("total");

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(orderDate);

      int date = calendar.get(Calendar.DAY_OF_MONTH);
      int index = date - 1;
      double newTotalPrice = response3Forms.get(index) + total;
      response3Forms.set(index, newTotalPrice);
    }

    return response3Forms;
  }

  @Override
  public double statisticalByMonthAndYear(int year) {
    try {
      return orderRepository.statistical(year);
    } catch (Exception e) {
      return 0;
    }
  }
}
