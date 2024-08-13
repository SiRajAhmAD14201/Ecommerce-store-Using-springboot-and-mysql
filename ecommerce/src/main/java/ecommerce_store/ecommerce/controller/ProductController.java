package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.ProductRequest;
import ecommerce_store.ecommerce.dto.response.ProductResponse;
import ecommerce_store.ecommerce.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/product")
@Tag(name = "Product Management System", description = "Operations pertaining to products in the Product Management System")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "View a list of available products", description = "Fetches all products from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct() {
        List<ProductResponse> productResponses = productService.findAllProduct();
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get a product by Id", description = "Fetches a product by its ID from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID to fetch the product object", required = true)
            @PathVariable Long id) {
        ProductResponse productResponse = productService.findProductById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Operation(summary = "Add a new product", description = "Creates a new product in the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created product")
    })
    @PostMapping
    public ResponseEntity<ProductRequest> saveProduct(
            @Parameter(description = "Product object to store in the database", required = true)
            @RequestBody ProductRequest productRequest) {
        ProductRequest productRequest1 = productService.saveProductRequest(productRequest);
        return new ResponseEntity<>(productRequest1, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Successfully deleted product"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID to delete the product object", required = true)
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}