package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.ProductRequest;
import ecommerce_store.ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAllProduct();
    ProductResponse findProductById(Long id);
    ProductRequest saveProductRequest(ProductRequest productRequest);
    ProductRequest updateProductRequest(Long id,ProductRequest productRequest);
    void deleteProduct(Long id);

}
