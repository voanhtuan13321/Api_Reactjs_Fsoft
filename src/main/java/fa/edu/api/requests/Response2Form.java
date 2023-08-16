package fa.edu.api.requests;

import fa.edu.api.entities.Book;
import lombok.*;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 14/08/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Response2Form {
  private Book book;
  private long quantity;
}
