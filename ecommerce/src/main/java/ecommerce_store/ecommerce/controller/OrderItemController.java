package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.OrderItemRequest;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;
import ecommerce_store.ecommerce.service.interfaces.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-item")
@Tag(name = "Order Item Management System", description = "Operations pertaining to order items in the Order Item Management System")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "View a list of available order items", description = "Fetches all order items from the database", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of order items")
    })
    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItem() {
        List<OrderItemResponse> orderItemResponse = orderItemService.findAllOrderItem();
        return new ResponseEntity<>(orderItemResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get order item by ID", description = "Fetches an order item by its ID from the database", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order item"),
            @ApiResponse(responseCode = "404", description = "Order item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(
            @Parameter(description = "ID of the order item to fetch", required = true)
            @PathVariable Long id) {
        OrderItemResponse orderItemResponse = orderItemService.findOrderItemById(id);
        return new ResponseEntity<>(orderItemResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create a new order item", description = "Creates a new order item in the database", responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created order item")
    })
    @PostMapping
    public ResponseEntity<OrderItemRequest> saveOrderItem(
            @Parameter(description = "Order item object to store in the database", required = true)
            @RequestBody OrderItemRequest orderItemRequest) {
        OrderItemRequest savedOrderItem = orderItemService.saveOrderItem(orderItemRequest);
        return new ResponseEntity<>(savedOrderItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete an order item by ID", description = "Deletes an order item from the database", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order item"),
            @ApiResponse(responseCode = "404", description = "Order item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(
            @Parameter(description = "ID of the order item to delete", required = true)
            @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
