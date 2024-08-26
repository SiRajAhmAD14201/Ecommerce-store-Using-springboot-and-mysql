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
@Table(name = "cart_items") // Optional: specify the table name if different from the default
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify the column name explicitly
    private Long id;

    @Column(name = "quantity") // Optional: specify the column name explicitly
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY) // Fetch type can be adjusted based on requirements
    @JoinColumn(name = "product_id") // Use snake_case for consistency
    private Product product;

    // Optional: Add additional fields or methods if needed
}
