package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.OrderDetailsRequest;
import ecommerce_store.ecommerce.dto.response.OrderDetailsResponse;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;
import ecommerce_store.ecommerce.dto.response.PaymentDetailsResponse;
import ecommerce_store.ecommerce.entities.OrderDetails;
import ecommerce_store.ecommerce.entities.OrderItem;
import ecommerce_store.ecommerce.entities.PaymentDetails;
import ecommerce_store.ecommerce.entities.User;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderDetailsRepo;
import ecommerce_store.ecommerce.repository.PaymentDetailsRepo;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.OrderDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsRepo orderDetailsRepo;
    private final UserRepo userRepo;
    private final PaymentDetailsRepo paymentDetailsRepo;
    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepo orderDetailsRepo, UserRepo userRepo, PaymentDetailsRepo paymentDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
        this.userRepo = userRepo;
        this.paymentDetailsRepo = paymentDetailsRepo;
    }




    @Override
    public List<OrderDetailsResponse> findAllOrderDetails() {
        return orderDetailsRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse findOrderDetailsById(Long id) {
        return orderDetailsRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found against this id: " + id));
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
        response.setUserId(orderDetails.getId());
        response.setTotal(orderDetails.getTotal());
        response.setPaymentId(orderDetails.getId());

        response.setOrderItems(orderDetails.getOrderItems().stream().map(this::toOrderItemResponse).collect(Collectors.toSet()));
         response.setPaymentDetails(toPaymentDetailsResponse(orderDetails.getPaymentDetails()));

        return response;
    }
     private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse orderItemResponse=new OrderItemResponse();
        orderItemResponse.setId(orderItem.getId());
        orderItemResponse.setOrderId(orderItemResponse.getOrderId());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setProductId(orderItemResponse.getProductId());

        return  orderItemResponse;
     }
     private PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails) {
         PaymentDetailsResponse response = new PaymentDetailsResponse();
         response.setId(paymentDetails.getId());
         response.setStatus(paymentDetails.getStatus());
         response.setAmount(paymentDetails.getAmount());

         return response;
     }
}

