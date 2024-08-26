package ecommerce_store.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long inventoryId;
    private double price;
    private Long discountId;


    // Getters and Setters
}