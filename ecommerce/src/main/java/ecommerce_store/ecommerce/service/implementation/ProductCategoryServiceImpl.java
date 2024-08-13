package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.ProductCategoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductCategoryResponse;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.entities.ProductCategory;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.ProductCategoryRepo;
import ecommerce_store.ecommerce.service.interfaces.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepo productCategoryRepo;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepo productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }

    @Override
    public List<ProductCategoryResponse> findAllProductCategory() {
        return productCategoryRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryResponse findProductCategoryById(Long id) {
        return productCategoryRepo.findById(id).map(this::toResponse).orElseThrow(() -> new ResourceNotFoundException("Product category not found against this " + id));
    }

    @Override
    public ProductCategoryRequest saveProductCategory(ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = toEntity(productCategoryRequest);
        ProductCategory savedCategory = productCategoryRepo.save(productCategory);
        return toRequest(savedCategory);
    }

    @Override
    public void deleteProductCategory(Long id) {
        Optional<ProductCategory> productCategory = productCategoryRepo.findById(id);
        if (productCategory.isPresent()) {
            productCategoryRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product category nor found against this " + id);
        }
    }


    // Convert Entity to Response DTO
    private ProductCategoryResponse toResponse(ProductCategory productCategory) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getDesc(),
                productCategory.getCreatedAt(),
                productCategory.getModifiedAt(),
                productCategory.getDeletedAt(),
                null // No need to include products in the response
        );
    }

    // Convert Request DTO to Entity
    private ProductCategory toEntity(ProductCategoryRequest productCategoryRequest) {
        return new ProductCategory(
                null, // Assuming the ID is auto-generated
                productCategoryRequest.getName(),
                productCategoryRequest.getDesc(),
                new Timestamp(System.currentTimeMillis()), // Set the creation time
                null, // ModifiedAt will be updated on future changes
                null, // DeletedAt will be updated if needed
                new HashSet<>() // Assuming products are added later
        );
    }

    // Convert Entity to Request DTO (useful for returning saved entity)
    private ProductCategoryRequest toRequest(ProductCategory productCategory) {
        return new ProductCategoryRequest(
                productCategory.getName(),
                productCategory.getDesc()
        );
    }

}

