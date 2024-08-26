package ecommerce_store.ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponse {
    private Long id;
    private Long userId;
    private double total;
    private Long paymentId;
    private UserResponse user;
    private Set<OrderItemResponse> orderItems;
    private PaymentDetailsResponse paymentDetails;

    // Getters and Setters
}