package ecommerce_store.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequest {
    private Long userId;
    private double total;
    private Long paymentId;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    // Getters and Setters
}