package ecommerce_store.ecommerce.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
    private String name;
    private String desc;
    private double discountPercent;
    private boolean active;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    // Getters and Setters
}
