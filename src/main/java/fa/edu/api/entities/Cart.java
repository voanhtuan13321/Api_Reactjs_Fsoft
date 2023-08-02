package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Cart entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_cart")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cartId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  private long quantity;
}
