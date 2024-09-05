package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserRequest;
import ecommerce_store.ecommerce.dto.response.*;
import ecommerce_store.ecommerce.entities.*;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderDetailsRepo;
import ecommerce_store.ecommerce.repository.UserAddressRepo;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
   private final UserRepo userRepo;
   private final UserAddressRepo userAddressRepo;
   private final OrderDetailsRepo orderDetailsRepo;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserAddressRepo userAddressRepo, OrderDetailsRepo orderDetailsRepo) {
        this.userRepo = userRepo;
        this.userAddressRepo = userAddressRepo;
        this.orderDetailsRepo = orderDetailsRepo;
    }



    @Override
    public List<UserResponse> findAllUser() {
       return userRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(Long id) {
        return userRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("User not found against this "+id));
    }

    @Override
    public UserRequest saveUser(UserRequest userRequest) {
        User user=new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        User savedUser = userRepo.save(user);
        return toRequest(savedUser);
    }

    @Override
    public UserRequest updateUser(Long id, UserRequest userRequest) {
        // Find the existing User entity or throw an exception if not found
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Update the fields of the existing User entity
        existingUser.setUsername(userRequest.getUsername());
        existingUser.setPassword(userRequest.getPassword()); // Ensure to handle password securely
        existingUser.setEmail(userRequest.getEmail());

        // Save the updated User entity
        User updatedUser = userRepo.save(existingUser);

        // Return the updated UserRequest
        return new UserRequest(
                updatedUser.getUsername(),
                updatedUser.getPassword(), // Ensure to handle password securely
                updatedUser.getEmail()
        );
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isPresent()){
            userRepo.deleteById(id);
        }else {
           throw new  ResourceNotFoundException("user not found against this "+id);
        }
    }
    private UserResponse toResponse(User user){
    UserResponse userResponse=new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setEmail(user.getEmail());
    userResponse.setUsername(user.getUsername());

        // Fetch addresses associated with the user and map to UserAddressResponse
        Set<UserAddressResponse> addressResponses = userAddressRepo.findByUserId(user.getId())
                .stream()
                .map(this::toUserAddressResponse)
                .collect(Collectors.toSet());
        userResponse.setAddresses(addressResponses);

        // Fetch order details associated with the user and map to OrderDetailsResponse
        Set<OrderDetailsResponse> orderResponses = orderDetailsRepo.findByUserId(user.getId())
                .stream()
                .map(this::toOrderDetailsResponse)
                .collect(Collectors.toSet());
        userResponse.setOrders(orderResponses);

        return userResponse;
    }

    private UserRequest toRequest(User user){
    UserRequest userRequest=new UserRequest();
    userRequest.setEmail(user.getEmail());
    userRequest.setPassword(user.getPassword());
    userRequest.setUsername(userRequest.getUsername());
    return userRequest;
    }
    private UserAddressResponse toUserAddressResponse(UserAddress address) {
        UserAddressResponse response = new UserAddressResponse();
        response.setId(address.getId());
        response.setUserId(address.getUser().getId());
        response.setAddress(address.getAddress());
        response.setCountry(address.getCountry());
        response.setCity(address.getCity());
        response.setPostalCode(address.getPostalCode());
        response.setMobile(address.getMobile());
        return response;
    }

    private OrderDetailsResponse toOrderDetailsResponse(OrderDetails order) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setPaymentId(order.getPaymentDetails().getId());
        response.setTotal(order.getTotal());

        // Map and set Order Items
        Set<OrderItemResponse> orderItemResponses = order.getOrderItems()
                .stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toSet());
        response.setOrderItems(orderItemResponses);

        // Map and set Payment Details
        PaymentDetailsResponse paymentDetailsResponse = toPaymentDetailsResponse(order.getPaymentDetails());
        response.setPaymentDetails(paymentDetailsResponse);

        return response;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());

        response.setQuantity(orderItem.getQuantity());

        return response;
    }

    private PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails) {
        PaymentDetailsResponse response = new PaymentDetailsResponse();
        response.setId(paymentDetails.getId());
        response.setAmount(paymentDetails.getAmount());
        response.setProvider(paymentDetails.getProvider());
        response.setStatus(paymentDetails.getStatus());
        return response;
    }

}
