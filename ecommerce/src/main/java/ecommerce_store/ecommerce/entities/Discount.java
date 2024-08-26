package ecommerce_store.ecommerce.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discounts") // Optional: specify the table name if different from the default
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify column name explicitly
    private Long id;

    @Column(name = "name") // Optional: specify column name explicitly
    private String name;

    @Column(name = "description") // Specify column name explicitly if needed
    private String description;

    @Column(name = "discount_percent") // Use snake_case for consistency
    private double discountPercent;

    @Column(name = "active") // Use snake_case for consistency
    private boolean active;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true) // Cascade and orphan removal settings
    private Set<Product> products;
}