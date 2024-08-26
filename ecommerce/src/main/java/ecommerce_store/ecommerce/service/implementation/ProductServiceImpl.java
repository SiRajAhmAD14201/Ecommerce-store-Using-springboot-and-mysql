package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.ProductRequest;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.entities.Discount;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.entities.ProductCategory;
import ecommerce_store.ecommerce.entities.ProductInventory;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.DiscountRepo;
import ecommerce_store.ecommerce.repository.ProductCategoryRepo;
import ecommerce_store.ecommerce.repository.ProductInventoryRepo;
import ecommerce_store.ecommerce.repository.ProductRepo;
import ecommerce_store.ecommerce.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductInventoryRepo productInventoryRepo;
    private final DiscountRepo discountRepo;
    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, ProductCategoryRepo productCategoryRepo, ProductInventoryRepo productInventoryRepo, DiscountRepo discountRepo) {
        this.productRepo = productRepo;
        this.productCategoryRepo = productCategoryRepo;
        this.productInventoryRepo = productInventoryRepo;
        this.discountRepo = discountRepo;
    }



    @Override
    public List<ProductResponse> findAllProduct() {
        return productRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findProductById(Long id) {
        return productRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found against " + id));
    }

    @Override
    public ProductRequest saveProductRequest(ProductRequest productRequest) {
        Product newProduct = new Product();
        toEntity(productRequest, newProduct);
// Save the newProduct to the repository
        productRepo.save(newProduct);
        return productRequest;
        }

    @Override
    public ProductRequest updateProductRequest(Long id, ProductRequest productRequest) {
        // Find the existing Product entity or throw an exception if not found
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for ID " + id));

        // Update the fields of the existing Product entity
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());

        // Set the category, inventory, and discount if provided
        if (productRequest.getCategoryId() != null) {
            ProductCategory category = productCategoryRepo.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found for ID " + productRequest.getCategoryId()));
            existingProduct.setCategory(category);
        }

        if (productRequest.getInventoryId() != null) {
            ProductInventory inventory = productInventoryRepo.findById(productRequest.getInventoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for ID " + productRequest.getInventoryId()));
            existingProduct.setInventory(inventory);
        }

        if (productRequest.getDiscountId() != null) {
            Discount discount = discountRepo.findById(productRequest.getDiscountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Discount not found for ID " + productRequest.getDiscountId()));
            existingProduct.setDiscount(discount);
        }

        existingProduct.setPrice(productRequest.getPrice());

        // Save the updated Product entity
        Product updatedProduct = productRepo.save(existingProduct);

        // Return the updated ProductRequest
        return new ProductRequest(
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getCategory() != null ? updatedProduct.getCategory().getId() : null,
                updatedProduct.getInventory() != null ? updatedProduct.getInventory().getId() : null,
                updatedProduct.getPrice(),
                updatedProduct.getDiscount() != null ? updatedProduct.getDiscount().getId() : null
        );
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            productRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Product not found against " + id);
        }
    }
    // Convert Entity to Response DTO
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory() != null ? product.getCategory().getId() : null, // Set categoryId
                product.getCategory() != null ? product.getCategory().getName() : null, // Set categoryName
                product.getInventory() != null ? product.getInventory().getId() : null, // Set inventoryId
                product.getInventory() != null ? product.getInventory().getQuantity() : 0, // Set inventoryQuantity
                product.getPrice(),
                product.getDiscount() != null ? product.getDiscount().getId() : null, // Set discountId
                product.getDiscount() != null ? product.getDiscount().getName() : null, // Set discountName
                product.getDiscount() != null ? product.getDiscount().getDiscountPercent() : 0 // Set discountPercent
        );
    }

    // Convert Request DTO to Entity
    private Product toEntity(ProductRequest productRequest, Product existingProduct) {
        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());

        // Set category, inventory, and discount if present
        if (productRequest.getCategoryId() != null) {
            ProductCategory category = productCategoryRepo.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(category);
        }

        if (productRequest.getInventoryId() != null) {
            ProductInventory inventory = productInventoryRepo.findById(productRequest.getInventoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
            existingProduct.setInventory(inventory);
        }

        if (productRequest.getDiscountId() != null) {
            Discount discount = discountRepo.findById(productRequest.getDiscountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
            existingProduct.setDiscount(discount);
        }

        existingProduct.setPrice(productRequest.getPrice());

        return existingProduct;
    }
    // Convert Request DTO to Entity



}
