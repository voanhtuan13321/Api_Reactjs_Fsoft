package fa.edu.api.services;

import fa.edu.api.entities.Cart;
import fa.edu.api.requests.CartForm;

import java.util.List;

/**
 * Cart service class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
public interface CartService {
  List<Cart> findAllByUserId(Long userId);

  boolean addProductToCart(CartForm cartForm);

  boolean removeProductFromCart(Long idCart);

  boolean updateQuantity(CartForm cartForm);
}
