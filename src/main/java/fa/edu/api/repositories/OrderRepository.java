package fa.edu.api.repositories;

import fa.edu.api.common.QueryString;
import fa.edu.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(value = QueryString.STATISTICAL, nativeQuery = true)
  List<Map<String, Object>> statistical(int year, int month);

  @Query(value = QueryString.STATISTICAL_YEAR, nativeQuery = true)
  double statistical(int year);
}