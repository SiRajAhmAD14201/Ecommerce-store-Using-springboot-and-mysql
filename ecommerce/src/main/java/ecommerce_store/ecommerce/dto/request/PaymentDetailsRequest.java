package ecommerce_store.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsRequest {
    private Long orderId;
    private double amount;
    private String provider;
    private String status;
}
