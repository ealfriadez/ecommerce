package pe.edu.unfv.microservices.productmicroservice.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.productmicroservice.category.CategoryService;
import pe.edu.unfv.microservices.productmicroservice.exceptions.ProductException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final ProductMapper mapper;

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    public Integer createProduct(ProductRequest request) {
        if(categoryService.getCategoryById(request.categoryId()) == null){
            throw new ProductException("Category with ID %s not found".formatted(request.categoryId()));
        }

        Product newProduct = mapper.toProduct(request);
        Product savedProduct = repository.save(newProduct);
        return savedProduct.getId();
    }

    public void deleteProduct(Integer id) {
        if(id==null){
            throw new ProductException("Product ID cannot be null");
        }

        if(!repository.existsById(id)){
            throw new ProductException("Product with ID %s not found".formatted(id));
        }
        repository.deleteById(id);
    }

    public Integer updateProduct(ProductRequest request) {
        if (request.id() == null){
            throw new ProductException("Product ID cannot be null");
        }

        if(categoryService.getCategoryById(request.categoryId()) == null){
            throw new ProductException("Category with ID %s not found".formatted(request.categoryId()));
        }

        Product existingProduct = repository.findById(request.id())
                .orElseThrow(()-> new ProductException("Product with ID %s not found".formatted(request.id())));

        Product updatedProduct = mapper.toProduct(request);

        //Ensure we do not overwrite stock
        updatedProduct.setStock(existingProduct.getStock());

        repository.save(updatedProduct);
        return updatedProduct.getId();
    }

    public ProductResponse getProductById(Integer id) {
        if (id == null) {
            throw new ProductException("Product ID cannot be null");
        }
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElse(null);
    }

    public List<ProductResponse> getProductsByCategoryId(Integer id) {
        if (id == null) {
            throw new ProductException("Category ID cannot be null");
        }
        return repository.findAll().stream()
                .filter(product -> product.getCategory().getId().equals(id))
                .map(mapper::toProductResponse)
                .toList();
    }

    @Transactional
    public void purchaseProduct(List<ProductQuantityRequest> request) {
        for (ProductQuantityRequest item : request) {

            Product product = repository.findById(item.productId())
                    .orElseThrow(() -> new ProductException("Product with ID %s not found".formatted(item.productId())));

            if (item.quantity() < 0) {
                throw new ProductException("Restock quantity cannot be negative for product ID %s".formatted(item.productId()));
            }

            if (product.getStock() < item.quantity()) {
                throw new ProductException("Insufficient stock for product ID %s".formatted(item.productId()));
            }

            product.setStock(product.getStock() - item.quantity());
            repository.save(product);
        }
    }

    @Transactional
    public void restockProduct(List<ProductQuantityRequest> request) {
        for (ProductQuantityRequest item : request) {
            Product product = repository.findById(item.productId())
                    .orElseThrow(() -> new ProductException("Product with ID %s not found".formatted(item.productId())));

            if (item.quantity() < 0) {
                throw new ProductException("Restock quantity cannot be negative for product ID %s".formatted(item.productId()));
            }

            product.setStock(product.getStock() + item.quantity());
            repository.save(product);
        }
    }
}
