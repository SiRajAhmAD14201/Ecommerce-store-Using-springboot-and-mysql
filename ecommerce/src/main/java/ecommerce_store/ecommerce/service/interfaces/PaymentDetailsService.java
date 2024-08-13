package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.PaymentDetailsRequest;
import ecommerce_store.ecommerce.dto.response.PaymentDetailsResponse;

import java.util.List;

public interface PaymentDetailsService {
    List<PaymentDetailsResponse> findAllPaymentDetails();
    PaymentDetailsResponse findPaymentDetailsById(Long id);
    PaymentDetailsRequest savePaymentDetails(PaymentDetailsRequest paymentDetailsRequest);
    void deletePaymentDetails(Long id);
}
