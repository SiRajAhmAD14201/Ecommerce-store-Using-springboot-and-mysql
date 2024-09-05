package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.ProductInventoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductInventoryResponse;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.entities.ProductInventory;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.ProductInventoryRepo;
import ecommerce_store.ecommerce.repository.ProductRepo;
import ecommerce_store.ecommerce.service.interfaces.ProductInventoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {
    private final ProductInventoryRepo productInventoryRepo;
    private final ProductRepo productRepo;
    @Autowired
    public ProductInventoryServiceImpl(ProductInventoryRepo productInventoryRepo, ProductRepo productRepo) {
        this.productInventoryRepo = productInventoryRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductInventoryResponse> findAllProductInventory() {
        return productInventoryRepo.findAll().stream()
                .map(inventory -> {
                    Set<Product> products = productRepo.findByInventoryId(inventory.getId());
                    return toResponse(inventory, products);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductInventoryResponse findByProductInventoryId(Long id) {
        ProductInventory productInventory = productInventoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product inventory not found against this " + id));

        Set<Product> products = productRepo.findByInventoryId(id);
        return toResponse(productInventory, products);
    }

    @Override
    @Transactional
    public ProductInventoryRequest saveProductInventory(ProductInventoryRequest productInventoryRequest) {
        ProductInventory productInventory = toEntity(productInventoryRequest);
        ProductInventory savedInventory = productInventoryRepo.save(productInventory);
        return toRequest(savedInventory);
    }

    @Override
    @Transactional
    public ProductInventoryRequest updateProductInventory(Long id, ProductInventoryRequest productInventoryRequest) {
        // Find the existing ProductInventory entity or throw an exception if not found
        ProductInventory existingProductInventory = productInventoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Inventory not found for ID " + id));

        // Update the fields with the values from the request
        existingProductInventory.setQuantity(productInventoryRequest.getQuantity());

        // Save the updated ProductInventory entity
        ProductInventory updatedProductInventory = productInventoryRepo.save(existingProductInventory);

        // Return the updated ProductInventoryRequest
        return new ProductInventoryRequest(
                updatedProductInventory.getQuantity()

        );
    }

    @Override
    @Transactional
    public void deleteProductInventory(Long id) {
        Optional<ProductInventory> productInventory=productInventoryRepo.findById(id);
        if (productInventory.isPresent()){
            productInventoryRepo.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Product inventory not found against this"+id);
        }

    }
    // Convert Entity to Response DTO


    // Convert Entity to Request DTO (useful for returning saved entity)
    private ProductInventoryRequest toRequest(ProductInventory productInventory) {
        return new ProductInventoryRequest(
                productInventory.getQuantity()
        );
    }
    // Convert Entity to Response DTO
    private ProductInventoryResponse toResponse(ProductInventory productInventory, Set<Product> products) {
        return new ProductInventoryResponse(
                productInventory.getId(),
                productInventory.getQuantity(),
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
    private ProductInventory toEntity(ProductInventoryRequest productInventoryRequest) {
        if (productInventoryRequest == null) {
            return null; // Handle null input
        }
        ProductInventory entity = new ProductInventory();
        entity.setQuantity(productInventoryRequest.getQuantity());

        return entity;
    }

}
