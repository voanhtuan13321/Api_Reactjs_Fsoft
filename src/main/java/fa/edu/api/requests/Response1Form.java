package fa.edu.api.requests;

import fa.edu.api.entities.User;
import lombok.Builder;
import lombok.Data;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 13/08/2023
 */
@Data
@Builder
public class Response1Form {
  private User user;
  private long quantity;
}
