package ecommerce_store.ecommerce.dto.response;


import ecommerce_store.ecommerce.entities.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private Long id;
    private String name;
    private String desc;
    private double discountPercent;
    private boolean active;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Timestamp deletedAt;
    private Set<ProductResponse> products;


    // Getters and Setters

}






