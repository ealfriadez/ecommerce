package pe.edu.unfv.microservices.productmicroservice.category;

import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.productmicroservice.product.ProductMapper;

@Service
public class CategoryMapper {

    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }
    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getProducts().stream()
                        .map(ProductMapper::toProductResponse)
                        .toList()
        );
    }
}
