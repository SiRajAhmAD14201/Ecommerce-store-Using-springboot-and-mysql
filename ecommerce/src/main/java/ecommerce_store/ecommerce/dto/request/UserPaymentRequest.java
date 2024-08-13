package ecommerce_store.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentRequest {
    private Long userId;
    private String paymentType;
    private String provider;
    private String accountNo;
    private Date expiry;}