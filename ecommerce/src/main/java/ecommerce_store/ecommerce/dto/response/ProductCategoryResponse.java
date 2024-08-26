package ecommerce_store.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
@Data
@AllArgsConstructor@NoArgsConstructor
public class ProductCategoryResponse {
    private Long id;
    private String name;
    private String description;

    private Set<ProductResponse> products;
}