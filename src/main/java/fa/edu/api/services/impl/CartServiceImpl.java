package fa.edu.api.services.impl;

import fa.edu.api.entities.Book;
import fa.edu.api.entities.Cart;
import fa.edu.api.entities.User;
import fa.edu.api.repositories.BookRepository;
import fa.edu.api.repositories.CartRepository;
import fa.edu.api.repositories.UserRepository;
import fa.edu.api.requests.CartForm;
import fa.edu.api.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cart service implement class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BookRepository bookRepository;

  /**
   * Get all products in cart of user.
   *
   * @param userId want to get all products
   * @return list products
   */
  @Override
  public List<Cart> findAllByUserId(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      return new ArrayList<>();
    }

    User user = optionalUser.get();
    return cartRepository.findAllByUser(user);
  }

  /**
   * Add products to the cart.
   *
   * @param cartForm wants to add products to the cart
   * @return status
   */
  @Override
  public boolean addProductToCart(CartForm cartForm) {
    Optional<User> optionalUser = userRepository.findById((cartForm.getUserId()));
    Optional<Book> optionalBook = bookRepository.findById((cartForm.getBookId()));

    if (optionalUser.isEmpty() || optionalBook.isEmpty()) {
      return false;
    }

    User user = optionalUser.get();
    Book book = optionalBook.get();
    Cart cart = Cart.builder()
        .user(user)
        .book(book)
        .quantity(cartForm.getQuantity())
        .build();
    cartRepository.save(cart);
    return true;
  }

  /**
   * Remove products from the cart.
   *
   * @param idCart wants to remove
   * @return status
   */
  @Override
  public boolean removeProductFromCart(Long idCart) {
    Optional<Cart> optionalCart = cartRepository.findById(idCart);
    if (optionalCart.isEmpty()) {
      return false;
    }
    Cart cart = optionalCart.get();
    cartRepository.delete(cart);
    return true;
  }
}
