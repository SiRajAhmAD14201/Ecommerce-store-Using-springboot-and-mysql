package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.ProductInventoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductInventoryResponse;
import ecommerce_store.ecommerce.service.interfaces.ProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product-inventory")
@Tag(name = "Product Inventory Management", description = "Operations pertaining to product inventories in the Product Inventory Management System")
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;

    @Autowired
    public ProductInventoryController(ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    @Operation(summary = "View a list of product inventories", description = "Fetches all product inventories from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<List<ProductInventoryResponse>> getAllProductInventory() {
        List<ProductInventoryResponse> productInventoryResponses = productInventoryService.findAllProductInventory();
        return new ResponseEntity<>(productInventoryResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get a product inventory by ID", description = "Fetches a product inventory by its ID from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved product inventory"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product inventory not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductInventoryResponse> getProductInventoryById(
            @Parameter(description = "Product Inventory ID to fetch the inventory object", required = true)
            @PathVariable Long id) {
        ProductInventoryResponse productInventoryResponse = productInventoryService.findByProductInventoryId(id);
        return new ResponseEntity<>(productInventoryResponse, HttpStatus.OK);
    }

    @Operation(summary = "Add a new product inventory", description = "Creates a new product inventory in the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created product inventory")
    })
    @PostMapping
    public ResponseEntity<ProductInventoryRequest> saveProductInventory(
            @Parameter(description = "Product Inventory object to store in the database", required = true)
            @RequestBody ProductInventoryRequest productInventoryRequest) {
        ProductInventoryRequest productInventoryRequest1 = productInventoryService.saveProductInventory(productInventoryRequest);
        return new ResponseEntity<>(productInventoryRequest1, HttpStatus.CREATED);
    }


    @Operation(summary = "Update Product Inventory", description = "Update an existing product inventory by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Inventory updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Product Inventory not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductInventoryRequest> updateProductInventory(
            @Parameter(description = "ID of the product inventory to update") @PathVariable Long id,
            @RequestBody ProductInventoryRequest productInventoryRequest) {

        ProductInventoryRequest updatedProductInventory = productInventoryService.updateProductInventory(id, productInventoryRequest);

        if (updatedProductInventory != null) {
            return new ResponseEntity<>(updatedProductInventory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a product inventory", description = "Deletes a product inventory from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Successfully deleted product inventory"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product inventory not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductInventory(
            @Parameter(description = "Product Inventory ID to delete the inventory object", required = true)
            @PathVariable Long id) {
        productInventoryService.deleteProductInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
