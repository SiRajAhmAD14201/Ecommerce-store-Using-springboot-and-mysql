package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.ProductRequest;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
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
@Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
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
                product.getDesc(),
                null, // categoryId is not set
                null, // categoryName is not set
                null, // inventoryId is not set
                0,    // inventoryQuantity is not set
                product.getPrice(),
                null, // discountId is not set
                null, // discountName is not set
                0,    // discountPercent is not set
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getDeletedAt()
        );
    }
    private Product toEntity(ProductRequest productRequest, Product existingProduct) {
        existingProduct.setName(productRequest.getName());
        existingProduct.setDesc(productRequest.getDesc());
        // Set category, inventory, and discount if needed
        // Assuming you handle them externally if required
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setCreatedAt(productRequest.getCreatedAt());
        existingProduct.setModifiedAt(productRequest.getModifiedAt());
        existingProduct.setDeletedAt(productRequest.getDeletedAt());

        return existingProduct;
    }
    // Convert Request DTO to Entity


    private ProductRequest toRequest(Product product) {
        return new ProductRequest(
                product.getName(),
                product.getDesc(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getInventory() != null ? product.getInventory().getId() : null,
                product.getPrice(),
                product.getDiscount() != null ? product.getDiscount().getId() : null,
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getDeletedAt()
        );
    }

}
