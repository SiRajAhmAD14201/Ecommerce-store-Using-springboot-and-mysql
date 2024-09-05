package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.ProductCategoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductCategoryResponse;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.entities.ProductCategory;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.ProductCategoryRepo;
import ecommerce_store.ecommerce.repository.ProductRepo;
import ecommerce_store.ecommerce.service.interfaces.ProductCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductRepo productRepo;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepo productCategoryRepo, ProductRepo productRepo) {
        this.productCategoryRepo = productCategoryRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductCategoryResponse> findAllProductCategory() {
        return productCategoryRepo.findAll().stream()
                .map(category -> {
                    Set<Product> products = productRepo.findByCategoryId(category.getId());
                    return toResponse(category, products);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryResponse findProductCategoryById(Long id) {
        ProductCategory productCategory = productCategoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product category not found against this " + id));

        Set<Product> products = productRepo.findByCategoryId(id);
        return toResponse(productCategory, products);
    }

    @Override
    @Transactional
    public ProductCategoryRequest saveProductCategory(ProductCategoryRequest productCategoryRequest) {
        ProductCategory productCategory = toEntity(productCategoryRequest);
        ProductCategory savedCategory = productCategoryRepo.save(productCategory);
        return toRequest(savedCategory);
    }

    @Override
    @Transactional
    public ProductCategoryRequest updateProductCategory(Long id, ProductCategoryRequest productCategoryRequest) {
        // Find the existing ProductCategory entity or throw an exception if not found
        ProductCategory existingProductCategory = productCategoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Category not found for ID " + id));

        // Update the fields with the values from the request
        existingProductCategory.setName(productCategoryRequest.getName());
        existingProductCategory.setDescription(productCategoryRequest.getDescription());

        // Save the updated ProductCategory entity
        ProductCategory updatedProductCategory = productCategoryRepo.save(existingProductCategory);

        // Return the updated ProductCategoryRequest
        return new ProductCategoryRequest(
                updatedProductCategory.getName(),
                updatedProductCategory.getDescription()
        );
    }

    @Override
    @Transactional
    public void deleteProductCategory(Long id) {
        Optional<ProductCategory> productCategory = productCategoryRepo.findById(id);
        if (productCategory.isPresent()) {
            productCategoryRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product category not found against this " + id);
        }
    }

    // Convert Entity to Response DTO
    private ProductCategoryResponse toResponse(ProductCategory productCategory, Set<Product> products) {
        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getDescription(),
                products.stream()
                        .map(this::toProductResponse)
                        .collect(Collectors.toSet())
        );
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    // Convert Request DTO to Entity
    private ProductCategory toEntity(ProductCategoryRequest productCategoryRequest) {
        return new ProductCategory(
                null, // Assuming the ID is auto-generated
                productCategoryRequest.getName(),
                productCategoryRequest.getDescription(),
                new HashSet<>() // Assuming products are added later
        );
    }

    // Convert Entity to Request DTO (useful for returning saved entity)
    private ProductCategoryRequest toRequest(ProductCategory productCategory) {
        return new ProductCategoryRequest(
                productCategory.getName(),
                productCategory.getDescription()
        );
    }
}
