package pe.edu.unfv.microservices.cartmicroservice.cartItem;

public record CartItemResponse(
        Integer productId,
        Integer quantity
) {
}
