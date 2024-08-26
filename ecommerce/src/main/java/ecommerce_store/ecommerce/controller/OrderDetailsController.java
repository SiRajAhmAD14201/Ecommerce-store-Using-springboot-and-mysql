package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.OrderDetailsRequest;
import ecommerce_store.ecommerce.dto.response.OrderDetailsResponse;
import ecommerce_store.ecommerce.service.interfaces.OrderDetailsService;
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
@RequestMapping("/api/order-details")
@Tag(name = "Order Details Management System", description = "Operations pertaining to order details in the Order Details Management System")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @Operation(summary = "View a list of available order details", description = "Fetches all order details from the database", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of order details")
    })
    @GetMapping
    public ResponseEntity<List<OrderDetailsResponse>> getAllOrderDetails() {
        List<OrderDetailsResponse> orderDetailsResponse = orderDetailsService.findAllOrderDetails();
        return ResponseEntity.ok(orderDetailsResponse);
    }

    @Operation(summary = "Get order details by ID", description = "Fetches order details by ID from the database", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order details"),
            @ApiResponse(responseCode = "404", description = "Order details not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetailsById(
            @Parameter(description = "ID of the order details to fetch", required = true)
            @PathVariable Long id) {
        OrderDetailsResponse orderDetailsResponse = orderDetailsService.findOrderDetailsById(id);
        return new ResponseEntity<>(orderDetailsResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create a new order details", description = "Creates a new order details entry in the database", responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created order details")
    })
    @PostMapping
    public ResponseEntity<OrderDetailsRequest> createOrderDetails(
            @Parameter(description = "Order details object to store in the database", required = true)
            @RequestBody OrderDetailsRequest orderDetailsRequest) {
        OrderDetailsRequest savedOrderDetails = orderDetailsService.saveOrderDetails(orderDetailsRequest);
        return new ResponseEntity<>(savedOrderDetails, HttpStatus.CREATED);
    }


    @Operation(summary = "Update Order Details", description = "Update an existing order detail by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Order Details not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailsRequest> updateOrderDetails(
            @Parameter(description = "ID of the order detail to update") @PathVariable Long id,
            @RequestBody OrderDetailsRequest orderDetailsRequest) {

        OrderDetailsRequest updatedOrderDetails = orderDetailsService.updateOrderDetails(id, orderDetailsRequest);

        if (updatedOrderDetails != null) {
            return new ResponseEntity<>(updatedOrderDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete order details by ID", description = "Deletes an order details entry from the database", responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted order details"),
            @ApiResponse(responseCode = "404", description = "Order details not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(
            @Parameter(description = "ID of the order details to delete", required = true)
            @PathVariable Long id) {
        orderDetailsService.deleteOrderDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
