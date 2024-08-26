package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserPaymentRequest;
import ecommerce_store.ecommerce.dto.response.UserPaymentResponse;
import ecommerce_store.ecommerce.dto.response.UserResponse;
import ecommerce_store.ecommerce.entities.User;
import ecommerce_store.ecommerce.entities.UserPayment;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.UserPaymentRepo;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.UserPaymentService;
import ecommerce_store.ecommerce.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private final UserPaymentRepo userPaymentRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserPaymentServiceImpl(UserPaymentRepo userPaymentRepo, UserRepo userRepo) {
        this.userPaymentRepo = userPaymentRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<UserPaymentResponse> findAllUserPayment() {
        return userPaymentRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserPaymentResponse findUserPaymentById(Long id) {
        return userPaymentRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User Payment not found for ID: " + id));
    }

    @Override
    public UserPaymentRequest saveUserPayment(UserPaymentRequest userPaymentRequest) {
        UserPayment userPayment = new UserPayment();
        userPayment.setPaymentType(userPaymentRequest.getPaymentType());
        userPayment.setProvider(userPaymentRequest.getProvider());
        userPayment.setAccountNo(userPaymentRequest.getAccountNo());

        // Convert expiry to Date if necessary
        Date expiryDate = new Date(userPaymentRequest.getExpiry().getTime());
        userPayment.setExpiry(expiryDate);

        // Retrieve and set User entity
        User user = userRepo.findById(userPaymentRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID " + userPaymentRequest.getUserId()));
        userPayment.setUser(user);

        // Save the entity
        UserPayment savedUserPayment = userPaymentRepo.save(userPayment);

        // Return request DTO
        return toRequest(savedUserPayment);
    }

    @Override
    public UserPaymentRequest updateUserPayment(Long id, UserPaymentRequest userPaymentRequest) {
        // Find the existing UserPayment entity or throw an exception if not found
        UserPayment existingUserPayment = userPaymentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Payment not found for ID " + id));

        // Update the fields of the existing UserPayment entity
        existingUserPayment.setPaymentType(userPaymentRequest.getPaymentType());
        existingUserPayment.setProvider(userPaymentRequest.getProvider());
        existingUserPayment.setAccountNo(userPaymentRequest.getAccountNo());

        // Convert expiry to Date if necessary
        Date expiryDate = new Date(userPaymentRequest.getExpiry().getTime());
        existingUserPayment.setExpiry(expiryDate);

        // Update the User relationship if the User ID is provided
        if (userPaymentRequest.getUserId() != null) {
            User user = userRepo.findById(userPaymentRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for ID " + userPaymentRequest.getUserId()));
            existingUserPayment.setUser(user);
        }

        // Save the updated UserPayment entity
        UserPayment updatedUserPayment = userPaymentRepo.save(existingUserPayment);

        // Return the updated UserPaymentRequest
        return new UserPaymentRequest(
                updatedUserPayment.getUser() != null ? updatedUserPayment.getUser().getId() : null,
                updatedUserPayment.getPaymentType(),
                updatedUserPayment.getProvider(),
                updatedUserPayment.getAccountNo(),
                updatedUserPayment.getExpiry()
        );
    }

    @Override
    public void deleteUserPayment(Long id) {
        if (userPaymentRepo.existsById(id)) {
            userPaymentRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User Payment not found for ID: " + id);
        }
    }

    private UserPaymentResponse toResponse(UserPayment userPayment) {
        UserPaymentResponse response = new UserPaymentResponse();
        response.setId(userPayment.getId());
        response.setUserId(userPayment.getUser().getId());
        response.setPaymentType(userPayment.getPaymentType());
        response.setProvider(userPayment.getProvider());
        response.setAccountNo(userPayment.getAccountNo());
        response.setExpiry(userPayment.getExpiry());
        return response;
    }

    private UserPaymentRequest toRequest(UserPayment userPayment) {
        UserPaymentRequest request = new UserPaymentRequest();
        request.setUserId(userPayment.getUser().getId());
        request.setPaymentType(userPayment.getPaymentType());
        request.setProvider(userPayment.getProvider());
        request.setAccountNo(userPayment.getAccountNo());
        request.setExpiry(userPayment.getExpiry());
        return request;
    }
    private User convertToUserEntity(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setUsername(userResponse.getUsername());
        user.setEmail(userResponse.getEmail());
        // Set other fields as needed
        return user;
    }
}
