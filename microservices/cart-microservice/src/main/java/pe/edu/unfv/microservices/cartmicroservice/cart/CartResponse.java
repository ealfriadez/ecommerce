package pe.edu.unfv.microservices.cartmicroservice.cart;

import pe.edu.unfv.microservices.cartmicroservice.cartItem.CartItemResponse;

import java.util.List;

public record CartResponse(
        String id,
        String customerId,
        List<CartItemResponse> cartItems
) {
}
