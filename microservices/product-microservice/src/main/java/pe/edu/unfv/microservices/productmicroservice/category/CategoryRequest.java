package pe.edu.unfv.microservices.productmicroservice.category;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoryRequest(
        Integer id,
        @NotNull(message = "Category name is required")
        String name,
        String description
) {
}
