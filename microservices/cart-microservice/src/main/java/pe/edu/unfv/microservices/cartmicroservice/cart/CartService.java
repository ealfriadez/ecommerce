package pe.edu.unfv.microservices.cartmicroservice.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.cartmicroservice.exceptions.CartException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartResponse getCartByCustomerId(String customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CartException("Cart for customer with id " +  customerId + " does not exist"));
        return cartMapper.toCartResponse(cart);
    }

    public void clearCart(String customerId) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CartException("Cart for customer with id " + customerId + " does not exist"));
        cartRepository.delete(cart);
    }
}
