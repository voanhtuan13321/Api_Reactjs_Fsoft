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
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(columnDefinition = "NVARCHAR(100)")
  private String name;

  @Column(columnDefinition = "NVARCHAR(100)")
  private String address;

  private String email;
  private String password;
  private String phone;
}
