package heritage.africca.accel_connect.service.Impl;

import heritage.africca.accel_connect.entity.Cart;
import heritage.africca.accel_connect.entity.CartItem;
import heritage.africca.accel_connect.entity.Meal;
import heritage.africca.accel_connect.repository.CartRepository;
import heritage.africca.accel_connect.repository.MealRepository;
import heritage.africca.accel_connect.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final MealRepository mealRepository;

    public CartServiceImpl(CartRepository cartRepository, MealRepository mealRepository) {
        this.cartRepository = cartRepository;
        this.mealRepository = mealRepository;
    }


    public Cart createCart() {
        return cartRepository.save(new Cart());
    }

    public Cart addMealToCart(Long cartId, Long mealId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found"));

        CartItem item = new CartItem();
        item.setMeal(meal);
        item.setQuantity(quantity);

        cart.addItem(item);

        return cartRepository.save(cart);
    }

    public Cart removeMealFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        cart.removeItem(item);
        return cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }
}
