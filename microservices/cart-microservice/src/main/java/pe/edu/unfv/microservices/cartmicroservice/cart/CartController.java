package pe.edu.unfv.microservices.cartmicroservice.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{customerId}/cart/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCartByCustomerId(@PathVariable("customerId") String customerId){
        CartResponse cartResponse = cartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@PathVariable("customerId") String customerId){
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}
