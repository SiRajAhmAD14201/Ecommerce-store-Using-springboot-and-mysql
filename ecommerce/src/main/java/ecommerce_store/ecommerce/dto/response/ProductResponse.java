package ecommerce_store.ecommerce.dto.response;

import ecommerce_store.ecommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String desc;
    private Long categoryId;
    private String categoryName;
    private Long inventoryId;
    private int inventoryQuantity;
    private double price;
    private Long discountId;
    private String discountName;
    private double discountPercent;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;




    // Getters and Setters
}