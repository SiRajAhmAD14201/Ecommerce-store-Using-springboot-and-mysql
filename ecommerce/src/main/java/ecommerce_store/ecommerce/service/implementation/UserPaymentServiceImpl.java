package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserPaymentRequest;
import ecommerce_store.ecommerce.dto.response.UserPaymentResponse;
import ecommerce_store.ecommerce.entities.UserPayment;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.UserPaymentRepo;
import ecommerce_store.ecommerce.service.interfaces.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
    private final UserPaymentRepo userPaymentRepo;
@Autowired
    public UserPaymentServiceImpl(UserPaymentRepo userPaymentRepo) {
        this.userPaymentRepo = userPaymentRepo;
    }

    @Override
    public List<UserPaymentResponse> findAllUserPayment() {
        return userPaymentRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserPaymentResponse findUserPaymentById(Long id) {
        return userPaymentRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("User Payment Not found against this "+id));
    }

    @Override
    public UserPaymentRequest saveUserPayment(UserPaymentRequest userPaymentRequest) {
        UserPayment userPayment=new UserPayment();
        userPayment.setUserId(userPaymentRequest.getUserId());
        userPayment.setPaymentType(userPaymentRequest.getPaymentType());
        userPayment.setProvider(userPaymentRequest.getProvider());
        userPayment.setAccountNo(userPaymentRequest.getAccountNo());
        userPayment.setExpiry((Date) userPaymentRequest.getExpiry());
        return toRequest(userPayment);
    }

    @Override
    public void deleteUserPayment(Long id) {
        Optional<UserPayment> userPaymentOptional=userPaymentRepo.findById(id);
        if (userPaymentOptional.isPresent()){
            userPaymentRepo.deleteById(id);
        }else {
            throw new ResourceNotFoundException("UserPayment info not found against this "+id);
        }

    }
    private UserPaymentRequest toRequest(UserPayment userPayment){
    UserPaymentRequest userPaymentRequest=new UserPaymentRequest();
    userPaymentRequest.setUserId(userPayment.getUserId());
    userPaymentRequest.setPaymentType(userPayment.getPaymentType());
    userPaymentRequest.setProvider(userPayment.getProvider());
    userPaymentRequest.setExpiry(userPayment.getExpiry());
    userPaymentRequest.setAccountNo(userPayment.getAccountNo());
    return userPaymentRequest;
    }

    private UserPaymentResponse toResponse(UserPayment userPayment){
    UserPaymentResponse userPaymentResponse=new UserPaymentResponse();
    userPaymentResponse.setId(userPayment.getId());
    userPaymentResponse.setUserId(userPayment.getUserId());
    userPaymentResponse.setPaymentType(userPayment.getPaymentType());
    userPaymentResponse.setProvider(userPayment.getProvider());
    userPaymentResponse.setExpiry(userPayment.getExpiry());
    userPaymentResponse.setAccountNo(userPayment.getAccountNo());
    return userPaymentResponse;
    }
}
