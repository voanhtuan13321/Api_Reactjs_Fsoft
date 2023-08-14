package fa.edu.api.services.impl;

import fa.edu.api.common.ImageFile;
import fa.edu.api.entities.Book;
import fa.edu.api.entities.Order;
import fa.edu.api.entities.OrderDetail;
import fa.edu.api.repositories.BookRepository;
import fa.edu.api.repositories.OrderDetailRepository;
import fa.edu.api.repositories.OrderRepository;
import fa.edu.api.requests.Response2Form;
import fa.edu.api.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Order detail service implement class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;
  private final OrderRepository orderRepository;
  private final BookRepository bookRepository;

  /**
   * Get all order details by order id.
   *
   * @param orderId want to get all order details
   * @return list of order details
   */
  @Override
  public List<OrderDetail> getAllOrderDetailByOrderId(Long orderId) {
    Optional<Order> optionalOrder = orderRepository.findById(orderId);

    if (optionalOrder.isEmpty()) {
      return new ArrayList<>();
    }

    Order order = optionalOrder.get();
    List<OrderDetail> list = new ArrayList<>();
    List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
    for (OrderDetail orderDetail : orderDetailList) {
      String image = orderDetail.getBook().getImageName();
      orderDetail.getBook().setImageName(ImageFile.URL_API_IMAGE + image);
      list.add(orderDetail);
    }
    return list;
  }

  /**
   * Get top best-selling book.
   *
   * @param topBook the top book
   * @return list of top
   */
  @Override
  public List<Response2Form> topBestSellingBook(int topBook) {
    Book book = null;
    Response2Form response2Form = null;
    List<Response2Form> list = new ArrayList<>();
    List<Map<String, Long>> result = orderDetailRepository.topBestSellingBook(topBook);

    for (Map<String, Long> rs : result) {
        book = bookRepository.findById(rs.get("book_id")).orElseThrow();
        response2Form = Response2Form.builder()
            .book(book)
            .quantity(rs.get("quantity"))
            .build();
        list.add(response2Form);
    }

    return list;
  }
}
