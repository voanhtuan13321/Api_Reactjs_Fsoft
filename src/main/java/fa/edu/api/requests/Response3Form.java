package fa.edu.api.requests;

import lombok.Builder;
import lombok.Data;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 14/08/2023
 */
@Data
@Builder
public class Response3Form {
  private int date;
  private double totalPrice;
}
