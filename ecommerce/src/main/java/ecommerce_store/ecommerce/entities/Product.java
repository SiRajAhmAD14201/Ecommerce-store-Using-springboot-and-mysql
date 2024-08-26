package ecommerce_store.ecommerce.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product") // Optional: specify the table name if different from the default
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id") // Ensure this matches the foreign key column in your database
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "inventory_id") // Ensure this matches the foreign key column in your database
    private ProductInventory inventory;

    @ManyToOne
    @JoinColumn(name = "discount_id") // Ensure this matches the foreign key column in your database
    private Discount discount;

    // No need for explicit getters and setters if using Lombok @Data
}