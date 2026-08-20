package pe.edu.unfv.microservices.cartmicroservice.cart;

import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.cartmicroservice.cartItem.CartItem;
import pe.edu.unfv.microservices.cartmicroservice.cartItem.CartItemResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {

        List<CartItemResponse> cartItemResponses = new ArrayList<>();

        for(CartItem cartItem : cart.getItems()) {
            cartItemResponses.add(new CartItemResponse(
                    cartItem.getProductId(),
                    cartItem.getQuantity()
            ));
        }

        return new CartResponse(cart.getId(), cart.getCustomerId(), cartItemResponses);
    }
}
