package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.PaymentDetailsRequest;
import ecommerce_store.ecommerce.dto.response.PaymentDetailsResponse;
import ecommerce_store.ecommerce.entities.PaymentDetails;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.PaymentDetailsRepo;
import ecommerce_store.ecommerce.service.interfaces.PaymentDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
    private  final PaymentDetailsRepo paymentDetailsRepo;

    public PaymentDetailsServiceImpl(PaymentDetailsRepo paymentDetailsRepo) {
        this.paymentDetailsRepo = paymentDetailsRepo;
    }

    @Override
    public List<PaymentDetailsResponse> findAllPaymentDetails() {
     return paymentDetailsRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentDetailsResponse findPaymentDetailsById(Long id) {
        return paymentDetailsRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("payment details not available against this "+id));
    }

    @Override
    public PaymentDetailsRequest savePaymentDetails(PaymentDetailsRequest paymentDetailsRequest) {
        PaymentDetails paymentDetails = toEntity(paymentDetailsRequest);
        PaymentDetails savedPaymentDetails = paymentDetailsRepo.save(paymentDetails);
        return toRequest(savedPaymentDetails);
    }
    public PaymentDetailsRequest updatePaymentDetails(Long id, PaymentDetailsRequest paymentDetailsRequest) {
        // Find the existing PaymentDetails entity or throw an exception if not found
        PaymentDetails existingPaymentDetails = paymentDetailsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment details not found for ID " + id));

        // Update the fields with the values from the request
        existingPaymentDetails.setAmount(paymentDetailsRequest.getAmount());
        existingPaymentDetails.setProvider(paymentDetailsRequest.getProvider());
        existingPaymentDetails.setStatus(paymentDetailsRequest.getStatus());

        // Save the updated PaymentDetails entity
        PaymentDetails updatedPaymentDetails = paymentDetailsRepo.save(existingPaymentDetails);

        // Return the updated PaymentDetailsRequest
        return new PaymentDetailsRequest(
                updatedPaymentDetails.getAmount(),
                updatedPaymentDetails.getProvider(),
                updatedPaymentDetails.getStatus()
        );
    }

    @Override
    public void deletePaymentDetails(Long id) {
        Optional<PaymentDetails> paymentDetails=paymentDetailsRepo.findById(id);
        if (paymentDetails.isPresent()) {
            paymentDetailsRepo.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Resource not found against this id: " + id);
        }
    }
    private PaymentDetailsResponse toResponse(PaymentDetails paymentDetails){
        PaymentDetailsResponse paymentDetailsResponse=new PaymentDetailsResponse();
        paymentDetailsResponse.setId(paymentDetails.getId());
        paymentDetailsResponse.setStatus(paymentDetails.getStatus());
        paymentDetailsResponse.setAmount(paymentDetails.getAmount());
        paymentDetailsResponse.setProvider(paymentDetails.getProvider());


        return paymentDetailsResponse;
    }

    private PaymentDetails toEntity(PaymentDetailsRequest paymentDetailsRequest) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(paymentDetailsRequest.getStatus());
        paymentDetails.setAmount(paymentDetailsRequest.getAmount());
        paymentDetails.setProvider(paymentDetailsRequest.getProvider());

        return paymentDetails;
    }

    private PaymentDetailsRequest toRequest(PaymentDetails paymentDetails) {
        PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest();
        paymentDetailsRequest.setStatus(paymentDetails.getStatus());
        paymentDetailsRequest.setAmount(paymentDetails.getAmount());
        paymentDetailsRequest.setProvider(paymentDetails.getProvider());


        return paymentDetailsRequest;
    }

}
