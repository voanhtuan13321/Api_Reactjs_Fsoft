package fa.edu.api.services.impl;

import fa.edu.api.common.ImageFile;
import fa.edu.api.entities.Book;
import fa.edu.api.entities.Cart;
import fa.edu.api.entities.User;
import fa.edu.api.repositories.BookRepository;
import fa.edu.api.repositories.CartRepository;
import fa.edu.api.repositories.UserRepository;
import fa.edu.api.requests.CartForm;
import fa.edu.api.services.CartService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;

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
    List<Cart> list = new ArrayList<>();
    List<Cart> listOfCart = cartRepository.findAllByUser(user);

    for (Cart cart : listOfCart) {
      String image = cart.getBook().getImageName();
      cart.getBook().setImageName(ImageFile.URL_API_IMAGE + image);
      list.add(cart);
    }

    return list;
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

    Optional<Cart> optionalCart = cartRepository.findByUserAndBook(user, book);

    Cart cart;

    // check exits products in the cart
    if (optionalCart.isPresent()) {
      cart = optionalCart.get();
      cart.setQuantity(cart.getQuantity() + cartForm.getQuantity());
    } else {
      cart = Cart.builder()
          .user(user)
          .book(book)
          .quantity(cartForm.getQuantity())
          .build();
    }

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

  /**
   * Update quantity.
   *
   * @param cartForm wants to update quantity
   * @return status
   */
  @Override
  public boolean updateQuantity(CartForm cartForm) {
    Optional<Cart> optionalCart = cartRepository.findById(cartForm.getCartId());

    if (optionalCart.isEmpty()) {
      return false;
    }

    Cart cart = optionalCart.get();
    cart.setQuantity(cartForm.getQuantity());

    cartRepository.save(cart);

    return true;
  }
}
