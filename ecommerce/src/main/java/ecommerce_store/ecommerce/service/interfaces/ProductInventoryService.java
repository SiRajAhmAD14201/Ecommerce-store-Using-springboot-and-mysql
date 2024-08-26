package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.ProductInventoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductInventoryResponse;

import java.util.List;

public interface ProductInventoryService {
    List<ProductInventoryResponse> findAllProductInventory();
    ProductInventoryResponse findByProductInventoryId(Long id);
    ProductInventoryRequest saveProductInventory(ProductInventoryRequest productInventoryRequest);
    ProductInventoryRequest updateProductInventory(Long id,ProductInventoryRequest productInventoryRequest);
    void deleteProductInventory(Long id);
}
