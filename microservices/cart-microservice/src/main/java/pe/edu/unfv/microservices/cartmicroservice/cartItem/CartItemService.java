package pe.edu.unfv.microservices.cartmicroservice.cartItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.cartmicroservice.cart.Cart;
import pe.edu.unfv.microservices.cartmicroservice.cart.CartRepository;
import pe.edu.unfv.microservices.cartmicroservice.customer.CustomerClient;
import pe.edu.unfv.microservices.cartmicroservice.customer.CustomerResponse;
import pe.edu.unfv.microservices.cartmicroservice.exceptions.CartException;
import pe.edu.unfv.microservices.cartmicroservice.product.ProductClient;
import pe.edu.unfv.microservices.cartmicroservice.product.ProductResponse;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartRepository cartRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public String addItemToCart(String customerId, CartItemRequest cartItemRequest) {

        CustomerResponse customerResponse = customerClient.getCustomerById(customerId)
                .orElseThrow(()-> new CartException("Customer with id " + customerId + " does not exist"));

        ProductResponse productResponse = productClient.getProductById(cartItemRequest.productId())
                .orElseThrow(()-> new CartException("Product with id " + cartItemRequest.productId() + " does not exist"));

        if(productResponse.stock() < cartItemRequest.quantity()) {
            throw new CartException("Product with id " + cartItemRequest.productId() + " does not have enough stock");
        }

        Cart cart = cartRepository.findByCustomerId(customerResponse.id())
                .orElse(Cart.builder()
                        .customerId(customerId)
                        .items(new ArrayList<>())
                        .build()
                );

        boolean productExists = cart.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(cartItemRequest.productId()));

        if(productExists) {
            throw new CartException("Product with id " + cartItemRequest.productId() + " already exists");
        }

        cart.getItems().add(
                CartItem.builder()
                        .productId(cartItemRequest.productId())
                        .quantity(cartItemRequest.quantity())
                        .build()
        );

        cartRepository.save(cart);

        return cart.getId();
    }

    public void updateItemFromCart(String customerId, CartItemRequest cartItemRequest) {

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CartException("Cart for customer with id " + customerId + " does not exist"));

        CartItem itemToUpate = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(cartItemRequest.productId()))
                .findFirst()
                .orElseThrow(()-> new CartException("Product with id " + cartItemRequest.productId() + " is not in the cart"));

        if(productClient.getProductById(cartItemRequest.productId()).get().stock() < cartItemRequest.quantity()) {
            throw new CartException("Product with id " + cartItemRequest.productId() + " does not have enough stock");
        }

        itemToUpate.setQuantity(cartItemRequest.quantity());

        cartRepository.save(cart);
    }

    public void removeItemFromCart(String customerId, Integer productId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new CartException("Cart for customer with id " + customerId + " does not exist"));

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProductId() == productId)
                .findFirst()
                .orElseThrow(()-> new CartException("Product with id " + productId + " is not in the cart"));

        cart.getItems().remove(itemToRemove);
        cartRepository.save(cart);
    }
}
