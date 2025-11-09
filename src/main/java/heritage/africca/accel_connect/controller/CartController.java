package heritage.africca.accel_connect.controller;

import heritage.africca.accel_connect.entity.Cart;
import heritage.africca.accel_connect.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/carts")
@CrossOrigin("*")
public class CartController {


    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart() {
        return cartService.createCart();
    }

    @PostMapping("/{cartId}/meals/{mealId}")
    public Cart addMealToCart(@PathVariable Long cartId, @PathVariable Long mealId, @RequestParam Integer quantity) {
        return cartService.addMealToCart(cartId, mealId, quantity);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public Cart removeMealFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        return cartService.removeMealFromCart(cartId, itemId);
    }

    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }

}
