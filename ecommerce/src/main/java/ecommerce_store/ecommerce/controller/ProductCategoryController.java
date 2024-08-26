package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.ProductCategoryRequest;
import ecommerce_store.ecommerce.dto.response.ProductCategoryResponse;
import ecommerce_store.ecommerce.service.interfaces.ProductCategoryService;
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
@RequestMapping("api/product-category")
@Tag(name = "Product Category Management", description = "Operations related to product categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Operation(summary = "Get all product categories", description = "Fetches all product categories from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping("/all")

    public ResponseEntity<List<ProductCategoryResponse>> getAllProductCategory(){
        List<ProductCategoryResponse> productCategoryResponses=productCategoryService.findAllProductCategory();
        return new ResponseEntity<>(productCategoryResponses, HttpStatus.OK);
    }
    @Operation(summary = "Get a product category by ID", description = "Fetches a product category by its ID from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product category"),
            @ApiResponse(responseCode = "404", description = "Product category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getProductById(
            @Parameter(description = "ID of the product category to be retrieved", required = true)
            @PathVariable Long id) {
        ProductCategoryResponse productCategoryResponse = productCategoryService.findProductCategoryById(id);
        return new ResponseEntity<>(productCategoryResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create a new product category", description = "Creates a new product category in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created product category")
    })
    @PostMapping
    public ResponseEntity<ProductCategoryRequest> saveProductCategory(
            @Parameter(description = "Product Category object to be saved", required = true)
            @RequestBody ProductCategoryRequest productCategoryRequest) {
        ProductCategoryRequest productCategoryRequest1 = productCategoryService.saveProductCategory(productCategoryRequest);
        return new ResponseEntity<>(productCategoryRequest1, HttpStatus.CREATED);
    }


    @Operation(summary = "Update Product Category", description = "Update an existing product category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Product Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryRequest> updateProductCategory(
            @Parameter(description = "ID of the product category to update") @PathVariable Long id,
            @RequestBody ProductCategoryRequest productCategoryRequest) {

        ProductCategoryRequest updatedProductCategory = productCategoryService.updateProductCategory(id, productCategoryRequest);

        if (updatedProductCategory != null) {
            return new ResponseEntity<>(updatedProductCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a product category", description = "Deletes a product category from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted product category"),
            @ApiResponse(responseCode = "404", description = "Product category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(
            @Parameter(description = "ID of the product category to be deleted", required = true)
            @PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}