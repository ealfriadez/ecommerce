package pe.edu.unfv.microservices.productmicroservice.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unfv.microservices.productmicroservice.exceptions.CategoryException;

import java.util.List;

import static org.hibernate.Hibernate.map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryResponse> getAllCategories(){
        return repository.findAll().stream()
                .map(mapper::toCategoryResponse)
                .toList();
    }

    public Integer createCategory(CategoryRequest request) {
        Category category = mapper.toCategory(request);
        return repository.save(category).getId();
    }

    public void deleteCategory(Integer id) {
        if (id == null){
            throw new CategoryException("Category ID cannot be null or blanck");
        } else if (!repository.existsById(id)) {
            throw new CategoryException("Category with ID %s not found".formatted(id));
        }
        repository.deleteById(id);
    }

    public Integer updateCategory(CategoryRequest request) {
        Category category = mapper.toCategory(request);
        if(request == null || request.id() == null){
            throw new CategoryException("Category ID cannot be null");
        } else if (!repository.existsById(request.id())) {
            throw new CategoryException("Category with ID %s not found".formatted(request.id()));
        }
        repository.save(category);
        return category.getId();
    }

    public CategoryResponse getCategoryById(Integer id) {
        return repository
                .findById(id)
                .map(mapper::toCategoryResponse)
                .orElseThrow(() -> new CategoryException("Category with ID %s not found".formatted(id)));
    }
}
