package heritage.africca.accel_connect.service;

import heritage.africca.accel_connect.entity.Cart;

public interface CartService {

    Cart createCart();
    Cart addMealToCart(Long cartId, Long mealId, Integer quantity);
    Cart removeMealFromCart(Long cartId, Long itemId);
    Cart getCart(Long cartId);


}
