package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.CartItemRequest;
import ecommerce_store.ecommerce.dto.response.CartItemResponse;
import ecommerce_store.ecommerce.service.interfaces.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@Tag(name = "Cart Item Management System", description = "Operations pertaining to cart items in the Cart Item Management System")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @Operation(summary = "View a list of available cart items", description = "Fetches all cart items from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems() {
        List<CartItemResponse> cartItems = cartItemService.findAllCartItem();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @Operation(summary = "Get a cart item by Id", description = "Fetches a cart item by its ID from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved cart item"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getCartItemById(
            @Parameter(description = "Cart Item ID to fetch the cart item object", required = true)
            @PathVariable Long id) {
        CartItemResponse cartItem = cartItemService.findCartItemById(id);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @Operation(summary = "Add a cart item", description = "Creates a new cart item in the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Successfully created cart item")
    })
    @PostMapping
    public ResponseEntity<CartItemRequest> createCartItem(
            @Parameter(description = "Cart Item object to store in the database", required = true)
            @RequestBody CartItemRequest cartItemRequest) {
        CartItemRequest savedCartItem = cartItemService.saveCartItem(cartItemRequest);
        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a cart item", description = "Updates an existing cart item in the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully updated cart item"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItemRequest> updateCartItem(
            @Parameter(description = "Cart Item ID to update the cart item object", required = true)
            @PathVariable Long id,
            @Parameter(description = "Cart Item object to update in the database", required = true)
            @RequestBody CartItemRequest cartItemRequest) {
        // Assuming that saveCartItem method is also used for updates
        // You might want to create a separate method for update if needed
        CartItemRequest updatedCartItem = cartItemService.saveCartItem(cartItemRequest);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }

    @Operation(summary = "Delete a cart item", description = "Deletes a cart item from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Successfully deleted cart item"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @Parameter(description = "Cart Item ID to delete the cart item object", required = true)
            @PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
