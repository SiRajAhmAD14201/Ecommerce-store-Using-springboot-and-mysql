package ecommerce_store.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsResponse {
    private Long id;
    private Long orderId;
    private double amount;
    private String provider;
    private String status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}