package fa.edu.api.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Title class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 31/07/2023
 */
@Entity
@Table(name = "tb_admin")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long adminId;

  private String email;
  private String password;
}
