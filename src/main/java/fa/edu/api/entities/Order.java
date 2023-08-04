package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order entity class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime orderDate;

  private boolean isConfirm;
}
