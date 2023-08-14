package fa.edu.api.requests;

import fa.edu.api.entities.Book;
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
public class Response2Form {
  private Book book;
  private long quantity;
}
