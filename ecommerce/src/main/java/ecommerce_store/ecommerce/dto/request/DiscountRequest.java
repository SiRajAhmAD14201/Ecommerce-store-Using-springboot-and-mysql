package ecommerce_store.ecommerce.dto.request;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private String name;
    private String description;
    private double discountPercent;
    private boolean active;
    // Getters and Setters
}
