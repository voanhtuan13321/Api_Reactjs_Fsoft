package fa.edu.api.controllers;

import fa.edu.api.entities.Cart;
import fa.edu.api.requests.CartForm;
import fa.edu.api.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Cart controller class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 03/08/2023
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/api/carts")
@Slf4j
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  /**
   * Get all products in cart of user.
   *
   * @param userId want to get all products
   * @return list products
   */
  @GetMapping(path = "/{userId}")
  public ResponseEntity<List<Cart>> getAllCartsByUserId(@PathVariable(name = "userId") Long userId) {
    log.info("get All Carts By User Id");
    List<Cart> carts = cartService.findAllByUserId(userId);
    return ResponseEntity.ok().body(carts);
  }

  /**
   * Add products to the cart.
   *
   * @param cartForm wants to add products to the cart
   * @return status
   */
  @PostMapping
  public ResponseEntity<Boolean> addProductToCart(@RequestBody CartForm cartForm) {
    log.info("add Product To Cart");
    boolean status = cartService.addProductToCart(cartForm);
    return ResponseEntity.ok().body(status);
  }

  /**
   * Update quantity.
   *
   * @param cartForm wants to update quantity
   * @return status
   */
  @PutMapping
  public ResponseEntity<Boolean> updateQuantity(@RequestBody CartForm cartForm) {
    log.info("add Product To Cart");
    boolean status = cartService.updateQuantity(cartForm);
    return ResponseEntity.ok().body(status);
  }

  /**
   * Remove products from the cart.
   *
   * @param idCart wants to remove
   * @return status
   */
  @DeleteMapping(path = "/{idCart}")
  public ResponseEntity<Boolean> removeProductFromCart(@PathVariable(name = "idCart") Long idCart) {
    log.info("remove Product From Cart");
    boolean status = cartService.removeProductFromCart(idCart);
    return ResponseEntity.ok().body(status);
  }

}
