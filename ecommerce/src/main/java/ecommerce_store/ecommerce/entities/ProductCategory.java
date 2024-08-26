package ecommerce_store.ecommerce.entities;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category") // Optional: specify the table name if different from the default
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify column name explicitly
    private Long id;

    @Column(name = "name") // Optional: specify column name explicitly
    private String name;

    @Column(name = "description") // Renamed column for clarity
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    // No need for explicit getters and setters if using Lombok @Data
}