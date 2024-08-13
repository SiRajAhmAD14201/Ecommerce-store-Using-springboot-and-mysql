package ecommerce_store.ecommerce.dto.response;

import ecommerce_store.ecommerce.entities.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Set<OrderDetailsResponse> orders;
    private Set<UserAddressResponse> addresses;
    private Set<UserPaymentResponse> payments;

}