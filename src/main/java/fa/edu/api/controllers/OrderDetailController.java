package fa.edu.api.controllers;

import fa.edu.api.entities.OrderDetail;
import fa.edu.api.requests.Response2Form;
import fa.edu.api.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order detail controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/order-details")
@Slf4j
@RequiredArgsConstructor
public class OrderDetailController {

  private final OrderDetailService orderDetailService;

  /**
   * Get all order details by order id.
   *
   * @param orderId want to get all order details
   * @return list of order details
   */
  @GetMapping(path = "{orderId}")
  public ResponseEntity<List<OrderDetail>> getAllOrderDetailByOrderId(@PathVariable(name = "orderId") Long orderId) {
    log.info("get All Order Detail By OrderId");
    List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetailByOrderId(orderId);
    return ResponseEntity.ok().body(orderDetails);
  }

  /**
   * Get top best-selling book.
   */
  @GetMapping(path = "top-best-selling")
  public ResponseEntity<List<Response2Form>> topBestSellingBook() {
    log.info("top best-selling book");
    return ResponseEntity.ok().body(orderDetailService.topBestSellingBook(5));
  }

}
