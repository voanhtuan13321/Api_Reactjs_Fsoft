package fa.edu.api.requests;

import lombok.*;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 01/08/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CategoryForm {
  private Long categoryId;
  private String categoryName;
}
