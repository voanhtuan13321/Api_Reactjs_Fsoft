package fa.edu.api.repositories;

import fa.edu.api.common.QueryString;
import fa.edu.api.entities.Order;
import fa.edu.api.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * Order detail repository class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 04/08/2023
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
  List<OrderDetail> findAllByOrder(Order order);

  @Query(value = QueryString.TOP_BEST_SELLING_BOOK, nativeQuery = true)
  List<Map<String, Long>> topBestSellingBook(int topBook);
}