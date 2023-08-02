package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Category entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  @Column(columnDefinition = "NVARCHAR(100)")
  private String categoryName;
}
