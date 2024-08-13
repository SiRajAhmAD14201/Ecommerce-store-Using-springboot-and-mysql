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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String desc;
    private Long categoryId;
    private Long inventoryId;
    private double price;
    private Long discountId;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;

    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "inventoryId", insertable = false, updatable = false)
    private ProductInventory inventory;

    @ManyToOne
    @JoinColumn(name = "discountId", insertable = false, updatable = false)
    private Discount discount;

    // getters and setters
}