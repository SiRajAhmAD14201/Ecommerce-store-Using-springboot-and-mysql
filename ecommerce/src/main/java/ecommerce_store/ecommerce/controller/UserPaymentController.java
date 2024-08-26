package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.UserPaymentRequest;
import ecommerce_store.ecommerce.dto.response.UserPaymentResponse;
import ecommerce_store.ecommerce.service.interfaces.UserPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/user-payment")
@Tag(name = "User Payment API", description = "Endpoints for managing user payments")
public class UserPaymentController {
    private final UserPaymentService userPaymentService;

    public UserPaymentController(UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }

    @Operation(summary = "Get all user payments", description = "Retrieve a list of all user payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @GetMapping
    public ResponseEntity<List<UserPaymentResponse>> getAllUserPayment() {
        List<UserPaymentResponse> userPaymentResponses = userPaymentService.findAllUserPayment();
        return new ResponseEntity<>(userPaymentResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get user payment by ID", description = "Retrieve a user payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserPaymentResponse> getUserPaymentById(
            @Parameter(description = "ID of the user payment to be retrieved", required = true)
            @PathVariable Long id) {
        UserPaymentResponse userPaymentResponse = userPaymentService.findUserPaymentById(id);
        return new ResponseEntity<>(userPaymentResponse, HttpStatus.OK);
    }

    @Operation(summary = "Save a new user payment", description = "Create and save a new user payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User payment created successfully")
    })
    @PostMapping
    public ResponseEntity<UserPaymentRequest> saveUserPayment(
            @Parameter(description = "Details of the user payment to be created", required = true)
            @RequestBody UserPaymentRequest userPaymentRequest) {
        UserPaymentRequest userPaymentRequest1 = userPaymentService.saveUserPayment(userPaymentRequest);
        return new ResponseEntity<>(userPaymentRequest1, HttpStatus.CREATED);
    }


    @Operation(summary = "Update User Payment", description = "Update an existing user payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User payment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserPaymentRequest> updateUserPayment(
            @Parameter(description = "ID of the user payment to update") @PathVariable Long id,
            @RequestBody UserPaymentRequest userPaymentRequest) {

        UserPaymentRequest updatedUserPayment = userPaymentService.updateUserPayment(id, userPaymentRequest);

        if (updatedUserPayment != null) {
            return new ResponseEntity<>(updatedUserPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a user payment by ID", description = "Delete a user payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPayment(
            @Parameter(description = "ID of the user payment to be deleted", required = true)
            @PathVariable Long id) {
        userPaymentService.deleteUserPayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}