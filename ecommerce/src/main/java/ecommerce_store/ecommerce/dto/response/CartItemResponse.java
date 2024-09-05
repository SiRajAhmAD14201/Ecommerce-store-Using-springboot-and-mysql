package ecommerce_store.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long productId;
    private int quantity;
    private String productName;  // Added field for product name
    private Double productPrice;
    private Double totalPrice;
    public void setTotalPrice() {
        this.totalPrice = quantity * productPrice;
    }
}