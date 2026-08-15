package pe.edu.unfv.microservices.productmicroservice.category;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.unfv.microservices.productmicroservice.product.Product;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}
