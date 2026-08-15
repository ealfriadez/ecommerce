package pe.edu.unfv.microservices.productmicroservice.category;

import pe.edu.unfv.microservices.productmicroservice.product.ProductResponse;

import java.util.List;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        List<ProductResponse> products
) {
}
