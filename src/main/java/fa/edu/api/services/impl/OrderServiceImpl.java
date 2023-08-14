package fa.edu.api.services.impl;

import fa.edu.api.entities.*;
import fa.edu.api.repositories.*;
import fa.edu.api.requests.Response3Form;
import fa.edu.api.requests.Response4Form;
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

  private static int compare(Order o1, Order o2) { return -o1.getOrderDate().compareTo(o2.getOrderDate()); }

  private static int compare2(Order o1, Order o2) { return -o1.getOrderDate().compareTo(o2.getOrderDate()); }

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
        .sorted(OrderServiceImpl::compare)
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
        .sorted(OrderServiceImpl::compare2)
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
  public List<Response3Form> statisticalByMonthAndYear(int month, int year) {
    int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
    Response3Form form = null;
    List<Response3Form> response3Forms = new ArrayList<>();
    List<Map<String, Object>> results = orderRepository.statistical();

    // create days of the month
    for (int i = 1; i <= daysInMonth; i++) {
      form = Response3Form.builder()
          .date(i)
          .totalPrice(0)
          .build();
      response3Forms.add(form);
    }

    for (Map<String, Object> result : results) {
      Timestamp orderDate = (Timestamp) result.get("order_date");
      double total = (double) result.get("total");

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(orderDate);

      int m = calendar.get(Calendar.MONTH);
      int y = calendar.get(Calendar.YEAR);

      if (m + 1 == month && y == year) {
        double newTotalPrice = response3Forms.get(m).getTotalPrice() + total;
        response3Forms.get(m).setTotalPrice(newTotalPrice);
      }
    }

    return response3Forms;
  }
}
