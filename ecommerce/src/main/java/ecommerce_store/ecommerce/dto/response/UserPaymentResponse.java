package ecommerce_store.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserPaymentResponse {
    private Long id;
    private Long userId;
    private String paymentType;
    private String provider;
    private String accountNo;
    private Date expiry;}
