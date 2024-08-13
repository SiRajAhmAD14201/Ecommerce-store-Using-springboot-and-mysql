package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.DiscountRequest;
import ecommerce_store.ecommerce.dto.response.DiscountResponse;
import ecommerce_store.ecommerce.service.interfaces.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
@Tag(name = "discount", description = "Operations related to discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/all")
    @Operation(summary = "View a list of available discounts", description = "Fetches all discounts from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<DiscountResponse>> showAllDiscount() {
        List<DiscountResponse> discountResponses = discountService.findAllDiscount();
        return ResponseEntity.ok(discountResponses);
    }
    @GetMapping("/{id}")
    @Operation(summary="View available discount by id",description = "Fetches discount by id from the database",responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "successfully retrieved discount by id"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",description = "discount not found against this id")
    })
    public ResponseEntity<DiscountResponse> discountById(@Parameter(description = "Cart Item ID to fetch the cart item object", required = true)
                                                             @PathVariable Long id){
        DiscountResponse discountResponse=discountService.findDiscountById(id);
        return new ResponseEntity<>(discountResponse, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary="add a discount",description = "add discount in the database",responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "successfully add discount")
    })
    public ResponseEntity<DiscountRequest> saveDiscount(@Parameter(description = "discount object to store in the database",required = true)@RequestBody DiscountRequest discountRequest){
        DiscountRequest discountRequest1=discountService.saveDiscount(discountRequest);
        return new ResponseEntity<>(discountRequest1,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a discount", description = "Update an existing discount in the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully updated discount"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<DiscountRequest> updateDiscount(
            @Parameter(description = "Discount ID to update the discount object", required = true) @PathVariable Long id,
            @Parameter(description = "Discount object to update in the database", required = true) @RequestBody DiscountRequest discountRequest) {
        DiscountRequest updatedDiscount = discountService.updateDiscount(id, discountRequest);
        if (updatedDiscount != null) {
            return new ResponseEntity<>(updatedDiscount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary="delete a discount",description = "discount id to delete discount from database",responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "successfully add discount")
    })
    public ResponseEntity<Void> deleteDiscount(@Parameter(description = "discount id to delete discount",required = true)@PathVariable Long id){
        discountService.deleteDiscount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}