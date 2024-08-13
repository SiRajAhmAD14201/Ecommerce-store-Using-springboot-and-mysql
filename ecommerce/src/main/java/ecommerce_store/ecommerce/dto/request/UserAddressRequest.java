package ecommerce_store.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class UserAddressRequest {
    private Long userId;
    private String address;
    private String city;
    private String postalCode;
    private String county;
    private String mobile;}