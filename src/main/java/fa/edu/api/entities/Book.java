package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Book entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(columnDefinition = "NVARCHAR(100)")
  private String title;

  @Column(columnDefinition = "NVARCHAR(255)")
  private String description;

  @Column(columnDefinition = "NVARCHAR(100)")
  private String author;

  private String imageName;
  private double price;
  private long quantity;
}
