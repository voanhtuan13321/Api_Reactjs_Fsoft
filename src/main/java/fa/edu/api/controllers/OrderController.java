package fa.edu.api.controllers;

import fa.edu.api.entities.Order;
import fa.edu.api.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  /**
   * Get all the orders Is confirmed.
   *
   * @return list of orders
   */
  @GetMapping(path = "/confirm")
  public ResponseEntity<List<Order>> getAllOrdersIsConfirmed() {
    log.info("get All Orders Is Confirmed");
    List<Order> orders = orderService.findAllIsConfirmed(true);
    return ResponseEntity.ok().body(orders);
  }

  /**
   * Get all the orders Is not confirmed.
   *
   * @return list of orders
   */
  @GetMapping(path = "/not-confirm")
  public ResponseEntity<List<Order>> getAllOrdersIsNotConfirmed() {
    log.info("get All Orders Is Not Confirmed");
    List<Order> orders = orderService.findAllIsConfirmed(false);
    return ResponseEntity.ok().body(orders);
  }

  /**
   * Get all the orders by user id and Is confirmed.
   *
   * @return list of orders
   */
  @GetMapping(path = "/users/confirm/{userId}")
  public ResponseEntity<List<Order>> getAllOrdersByUserIdAndIsConfirmed(@PathVariable(name = "userId") Long userId) {
    log.info("get All Orders By UserId");
    List<Order> orders = orderService.findAllByUserIdAndIsConfirmed(userId, true);
    return ResponseEntity.ok().body(orders);
  }

  /**
   * Get all the orders by user id and Is not confirmed.
   *
   * @return list of orders
   */
  @GetMapping(path = "/users/not-confirm/{userId}")
  public ResponseEntity<List<Order>> getAllOrdersByUserIdAndIsNotConfirmed(@PathVariable(name = "userId") Long userId) {
    log.info("get All Orders By UserId");
    List<Order> orders = orderService.findAllByUserIdAndIsConfirmed(userId, false);
    return ResponseEntity.ok().body(orders);
  }

  /**
   * Create new order.
   *
   * @param userId want to create
   * @return status
   */
  @PostMapping(path = "/{userId}")
  public ResponseEntity<Boolean> createNewOrder(@PathVariable(name = "userId") Long userId) {
    log.info("create New Order" + userId);
    boolean status = orderService.createNewOrder(userId);
    return ResponseEntity.ok().body(status);
  }

  /**
   * Update confirm order.
   *
   * @param idOrder want to confirm
   */
  @PutMapping(path = "/confirm/{idOrder}")
  public ResponseEntity<String> updateConfirmOrder(@PathVariable(name = "idOrder") Long idOrder) {
    log.info("create New Order");
    orderService.updateConfirmOrder(idOrder);
    return ResponseEntity.ok().build();
  }

  /**
   * Statistical.
   *
   * @param month the month want to statistic
   * @param year the year want to statistic
   * @return statistical
   */
  @GetMapping(path = "/statistical/{month}/{year}")
  public ResponseEntity<List<Double>> statistical(
      @PathVariable(name = "month") int month,
      @PathVariable(name = "year") int year
  ) {
    log.info("Statistical");
    List<Double> statistical = orderService.statisticalByMonthAndYear(month, year);
    return ResponseEntity.ok().body(statistical);
  }

  /**
   * Statistical.
   *
   * @param year the year want to statistic
   * @return statistical
   */
  @GetMapping(path = "/statistical/year/{year}")
  public ResponseEntity<Double> statistical(@PathVariable(name = "year") int year) {
    log.info("Statistical");
    double statistical = orderService.statisticalByMonthAndYear(year);
    return ResponseEntity.ok().body(statistical);
  }

}
