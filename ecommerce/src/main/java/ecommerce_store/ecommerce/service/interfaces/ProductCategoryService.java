package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.ProductCategoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductCategoryResponse;
import ecommerce_store.ecommerce.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryResponse>findAllProductCategory();
    ProductCategoryResponse findProductCategoryById(Long id);
    ProductCategoryRequest saveProductCategory(ProductCategoryRequest productCategoryRequest);
    void deleteProductCategory(Long id);
}
