package fa.edu.api.requests;

import lombok.*;

/**
 * Cart form class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CartForm {
  private Long userId;
  private Long bookId;
  private int quantity;
}
