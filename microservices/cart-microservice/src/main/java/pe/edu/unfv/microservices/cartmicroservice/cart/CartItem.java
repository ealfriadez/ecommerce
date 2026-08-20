package pe.edu.unfv.microservices.cartmicroservice.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItem {
    private Integer productId;
    private Integer quantity;

}
