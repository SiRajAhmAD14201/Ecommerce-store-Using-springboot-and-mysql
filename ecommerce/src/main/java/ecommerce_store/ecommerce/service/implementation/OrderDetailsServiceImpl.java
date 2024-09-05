package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.OrderDetailsRequest;
import ecommerce_store.ecommerce.dto.response.*;
import ecommerce_store.ecommerce.entities.*;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderDetailsRepo;
import ecommerce_store.ecommerce.repository.OrderItemRepo;
import ecommerce_store.ecommerce.repository.PaymentDetailsRepo;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.OrderDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsRepo orderDetailsRepo;
    private final UserRepo userRepo;
    private final PaymentDetailsRepo paymentDetailsRepo;
    private final OrderItemRepo orderItemRepo;
@Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepo orderDetailsRepo, UserRepo userRepo, PaymentDetailsRepo paymentDetailsRepo, OrderItemRepo orderItemRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
        this.userRepo = userRepo;
        this.paymentDetailsRepo = paymentDetailsRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public List<OrderDetailsResponse> findAllOrderDetails() {
        return orderDetailsRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse findOrderDetailsById(Long id) {
        OrderDetails orderDetails = orderDetailsRepo.findByIdWithUserAndOrderItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found against this id: " + id));

        return toResponse(orderDetails);
    }
    @Override
    @Transactional
    public OrderDetailsRequest saveOrderDetails(OrderDetailsRequest orderDetailsRequest) {
        // Fetch User and PaymentDetails entities from the repositories
        User user = userRepo.findById(orderDetailsRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentDetails paymentDetails = paymentDetailsRepo.findById(orderDetailsRequest.getPaymentId())
                .orElseThrow(() -> new RuntimeException("PaymentDetails not found"));

        // Create a new OrderDetails entity
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setTotal(orderDetailsRequest.getTotal());
        orderDetails.setUser(user);
        orderDetails.setPaymentDetails(paymentDetails);

        // Save the entity
        OrderDetails savedOrderDetails = orderDetailsRepo.save(orderDetails);

        // Map saved entity back to request
        OrderDetailsRequest savedOrderDetailsRequest = new OrderDetailsRequest();
        savedOrderDetailsRequest.setUserId(savedOrderDetails.getUser().getId());
        savedOrderDetailsRequest.setTotal(savedOrderDetails.getTotal());
        savedOrderDetailsRequest.setPaymentId(savedOrderDetails.getPaymentDetails().getId());

        return savedOrderDetailsRequest;
    }

    @Override
    @Transactional
    public OrderDetailsRequest updateOrderDetails(Long id, OrderDetailsRequest orderDetailsRequest) {
        OrderDetails orderDetails = orderDetailsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDetails not found for id: " + id));

        // Update the fields of the existing orderDetails object
        orderDetails.setTotal(orderDetailsRequest.getTotal());

        // Handle associated entities (if needed)
        if (orderDetailsRequest.getUserId() != null) {
            orderDetails.setUser(userRepo.findById(orderDetailsRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + orderDetailsRequest.getUserId())));
        }
        if (orderDetailsRequest.getPaymentId() != null) {
            orderDetails.setPaymentDetails(paymentDetailsRepo.findById(orderDetailsRequest.getPaymentId())
                    .orElseThrow(() -> new ResourceNotFoundException("PaymentDetails not found for id: " + orderDetailsRequest.getPaymentId())));
        }

        // Save updated orderDetails
        OrderDetails updatedOrderDetails = orderDetailsRepo.save(orderDetails);

        // Convert to request DTO and return
        return toRequest(updatedOrderDetails);
    }

    // Convert Entity to Request DTO
    private OrderDetailsRequest toRequest(OrderDetails orderDetails) {
        return new OrderDetailsRequest(
                orderDetails.getUser() != null ? orderDetails.getUser().getId() : null,
                orderDetails.getTotal(),
                orderDetails.getPaymentDetails() != null ? orderDetails.getPaymentDetails().getId() : null
        );
    }

    @Override
    public void deleteOrderDetails(Long id) {
        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepo.findById(id);
        if (orderDetailsOptional.isPresent()) {
            orderDetailsRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Resource not found against this id: " + id);
        }
    }

    private OrderDetailsResponse toResponse(OrderDetails orderDetails) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setId(orderDetails.getId());
        response.setUserId(orderDetails.getUser() != null ? orderDetails.getUser().getId() : null);
        response.setPaymentId(orderDetails.getPaymentDetails() != null ? orderDetails.getPaymentDetails().getId() : null);
        response.setTotal(orderDetails.getTotal());

        // Ensure orderItems are being mapped correctly
        response.setOrderItems(orderDetails.getOrderItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toSet()));
        response.setPaymentDetails(toPaymentDetailsResponse(orderDetails.getPaymentDetails()));

        // Map user details if needed
        if (orderDetails.getUser() != null) {
            response.setUser(toUserResponse(orderDetails.getUser()));
        }


        return response;
    }

    private UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        // Map addresses
        if (user.getAddresses() != null) {
            Set<UserAddressResponse> addressResponses = user.getAddresses().stream()
                    .map(this::toAddressResponse)
                    .collect(Collectors.toSet()); // Collect to Set
            userResponse.setAddresses(addressResponses);
        }

        // Map orders
        if (user.getOrders() != null) {
            Set<OrderDetailsResponse> orderDetailsResponses = user.getOrders().stream()
                    .map(this::toOrderDetailsResponse)
                    .collect(Collectors.toSet());
            userResponse.setOrders(orderDetailsResponses);
        }

        return userResponse;
    }

    private UserAddressResponse toAddressResponse(UserAddress address) {
        UserAddressResponse addressResponse = new UserAddressResponse();
        addressResponse.setId(address.getId());
        addressResponse.setCity(address.getCity());
        addressResponse.setAddress(address.getAddress());
        addressResponse.setMobile(address.getMobile());
        addressResponse.setCountry(address.getCountry());
        addressResponse.setPostalCode(address.getPostalCode());


        return addressResponse;
    }

    private OrderDetailsResponse toOrderDetailsResponse(OrderDetails orderDetails) {
        OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
        orderDetailsResponse.setId(orderDetails.getId());
        orderDetailsResponse.setUserId(orderDetails.getUser().getId());
        orderDetailsResponse.setTotal(orderDetails.getTotal());
        orderDetailsResponse.setPaymentId(orderDetails.getPaymentDetails().getId());
        orderDetailsResponse.setUser(toUserResponse(orderDetails.getUser())); // Avoid circular reference

        // Map order items
        if (orderDetails.getOrderItems() != null) {
            Set<OrderItemResponse> orderItemResponses = orderDetails.getOrderItems().stream()
                    .map(this::toOrderItemResponse)
                    .collect(Collectors.toSet());
            orderDetailsResponse.setOrderItems(orderItemResponses);
        }

        // Map payment details
        orderDetailsResponse.setPaymentDetails(toPaymentDetailsResponse(orderDetails.getPaymentDetails()));

        return orderDetailsResponse;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setId(orderItem.getId());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setProductId(orderItem.getProduct().getId());
        return orderItemResponse;
    }

    private PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails) {
        PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
        paymentDetailsResponse.setId(paymentDetails.getId());
        paymentDetailsResponse.setAmount(paymentDetails.getAmount());
        paymentDetailsResponse.setProvider(paymentDetails.getProvider());
        paymentDetailsResponse.setStatus(paymentDetails.getStatus());
        return paymentDetailsResponse;
    }
}

