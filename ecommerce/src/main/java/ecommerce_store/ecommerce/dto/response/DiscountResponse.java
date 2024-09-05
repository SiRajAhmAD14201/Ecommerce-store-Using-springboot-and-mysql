package ecommerce_store.ecommerce.dto.response;


import ecommerce_store.ecommerce.entities.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private Long id;
    private String name;
    private String description;
    private double discountPercent;
    private boolean active;
    private List<ProductResponse> products;

    // Getters and Setters

}






