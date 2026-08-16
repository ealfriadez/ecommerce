package pe.edu.unfv.microservices.productmicroservice.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unfv.microservices.productmicroservice.category.CategoryRequest;
import pe.edu.unfv.microservices.productmicroservice.category.CategoryResponse;
import pe.edu.unfv.microservices.productmicroservice.category.CategoryService;
import pe.edu.unfv.microservices.productmicroservice.exceptions.ProductException;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Integer> updateProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(request));
    }

    @GetMapping("/category/{id}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable Integer id) {
        return service.getProductsByCategoryId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getProductById(id));
    }
}
