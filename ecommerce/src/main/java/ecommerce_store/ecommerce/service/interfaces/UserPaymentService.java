package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.UserPaymentRequest;
import ecommerce_store.ecommerce.dto.response.UserPaymentResponse;

import java.util.List;

public interface UserPaymentService {
    List<UserPaymentResponse> findAllUserPayment();
    UserPaymentResponse findUserPaymentById(Long id);
    UserPaymentRequest saveUserPayment(UserPaymentRequest userPaymentRequest);
    void deleteUserPayment(Long id);
}
