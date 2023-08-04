package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Order detail entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_order_detail")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private long quantity;
}
