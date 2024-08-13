package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.UserAddressRequest;
import ecommerce_store.ecommerce.dto.response.UserAddressResponse;
import ecommerce_store.ecommerce.service.interfaces.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user-address")
@Tag(name = "User Address Management", description = "Operations related to managing user addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @Autowired
    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @Operation(summary = "Get all user addresses", description = "Retrieve a list of all user addresses")
    @GetMapping
    public ResponseEntity<List<UserAddressResponse>> getAllUserAddress() {
        List<UserAddressResponse> userAddressResponses = userAddressService.findAllUserAddress();
        return new ResponseEntity<>(userAddressResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get user address by ID", description = "Retrieve a specific user address by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserAddressResponse> getUserAddressById(
            @Parameter(description = "ID of the user address to retrieve", required = true)
            @PathVariable Long id) {
        UserAddressResponse userAddressResponse = userAddressService.findUserAddressById(id);
        return new ResponseEntity<>(userAddressResponse, HttpStatus.OK);
    }

    @Operation(summary = "Save a new user address", description = "Create and save a new user address")
    @PostMapping
    public ResponseEntity<UserAddressRequest> saveUserAddress(
            @Parameter(description = "User Address object to save", required = true)
            @RequestBody UserAddressRequest userAddressRequest) {
        UserAddressRequest userAddressRequest1 = userAddressService.saveUserAddress(userAddressRequest);
        return new ResponseEntity<>(userAddressRequest1, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a user address", description = "Delete a user address by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAddress(
            @Parameter(description = "ID of the user address to delete", required = true)
            @PathVariable Long id) {
        userAddressService.deleteUserAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

