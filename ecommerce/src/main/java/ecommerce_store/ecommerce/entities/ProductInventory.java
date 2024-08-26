package ecommerce_store.ecommerce.entities;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;
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
@Table(name = "product_inventory") // Optional: specify the table name if different from the default
public class ProductInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify column name explicitly
    private Long id;

    @Column(name = "quantity") // Optional: specify column name explicitly
    private int quantity;

    @OneToMany(mappedBy = "inventory")
    private Set<Product> products;

    // No need for explicit getters and setters if using Lombok @Data
}