package fa.edu.api.requests;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 14/08/2023
 */
@Data
@Builder
public class Response4Form {
  private Timestamp orderDate;
  private long total;
}
