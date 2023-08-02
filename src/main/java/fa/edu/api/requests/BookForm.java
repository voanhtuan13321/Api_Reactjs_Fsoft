package fa.edu.api.requests;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Book entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookForm {
  private Long bookId;
  private Long categoryId;
  private String title;
  private String description;
  private String author;
  private MultipartFile image;
  private double price;
  private long quantity;
}
