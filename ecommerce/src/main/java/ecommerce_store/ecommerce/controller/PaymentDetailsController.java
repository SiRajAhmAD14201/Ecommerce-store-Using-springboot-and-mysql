package ecommerce_store.ecommerce.controller;

import ecommerce_store.ecommerce.dto.request.PaymentDetailsRequest;
import ecommerce_store.ecommerce.dto.response.PaymentDetailsResponse;
import ecommerce_store.ecommerce.service.interfaces.PaymentDetailsService;
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
@RequestMapping("api/payment-details")
@Tag(name = "Payment Details Management", description = "Operations pertaining to payment details in the Payment Details Management System")
public class PaymentDetailsController {
    private final PaymentDetailsService paymentDetailsService;
    @Autowired
    public PaymentDetailsController(PaymentDetailsService paymentDetailsService) {
        this.paymentDetailsService = paymentDetailsService;
    }
    @Operation(summary = "View a list of available payment details", description = "Fetches all payment details from the database")
    @GetMapping
    public ResponseEntity<List<PaymentDetailsResponse>> getAllPaymentDetails(){
       List<PaymentDetailsResponse> paymentDetailsResponses=paymentDetailsService.findAllPaymentDetails();
       return new  ResponseEntity<>(paymentDetailsResponses, HttpStatus.OK);
    }
    @Operation(summary = "Get payment details by Id", description = "Fetches payment details by its ID from the database")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetailsById(
            @Parameter(description = "Payment Details ID to fetch the details object", required = true)
            @PathVariable Long id){
        PaymentDetailsResponse paymentDetailsResponse = paymentDetailsService.findPaymentDetailsById(id);
        return new ResponseEntity<>(paymentDetailsResponse, HttpStatus.OK);
    }



    @Operation(summary = "Add payment details", description = "Creates new payment details in the database")
    @PostMapping
    public ResponseEntity<PaymentDetailsRequest> savePaymentDetails(
            @Parameter(description = "Payment Details object to store in the database", required = true)
            @RequestBody PaymentDetailsRequest paymentDetailsRequest){
        PaymentDetailsRequest paymentDetailsRequest1=paymentDetailsService.savePaymentDetails(paymentDetailsRequest);
        return new ResponseEntity<>(paymentDetailsRequest1,HttpStatus.CREATED);
    }




    @Operation(summary = "Update Payment Details", description = "Update existing payment details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment Details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Payment Details not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDetailsRequest> updatePaymentDetails(
            @Parameter(description = "ID of the payment details to update") @PathVariable Long id,
            @RequestBody PaymentDetailsRequest paymentDetailsRequest) {

        PaymentDetailsRequest updatedPaymentDetails = paymentDetailsService.updatePaymentDetails(id, paymentDetailsRequest);

        if (updatedPaymentDetails != null) {
            return new ResponseEntity<>(updatedPaymentDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Delete payment details", description = "Deletes payment details from the database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDetails(
            @Parameter(description = "Payment Details ID to delete the details object", required = true)
            @PathVariable Long id){
        paymentDetailsService.deletePaymentDetails(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
