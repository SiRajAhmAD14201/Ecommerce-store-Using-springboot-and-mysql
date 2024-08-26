package ecommerce_store.ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
@Data
@AllArgsConstructor@NoArgsConstructor
public class ProductInventoryResponse {
    private Long id;
    private int quantity;
    private Set<ProductResponse> products;
}