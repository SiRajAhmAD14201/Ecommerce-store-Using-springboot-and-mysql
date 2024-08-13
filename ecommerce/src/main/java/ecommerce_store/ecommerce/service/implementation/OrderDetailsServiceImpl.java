package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.OrderDetailsRequest;
import ecommerce_store.ecommerce.dto.response.OrderDetailsResponse;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;
import ecommerce_store.ecommerce.dto.response.PaymentDetailsResponse;
import ecommerce_store.ecommerce.entities.OrderDetails;
import ecommerce_store.ecommerce.entities.OrderItem;
import ecommerce_store.ecommerce.entities.PaymentDetails;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderDetailsRepo;
import ecommerce_store.ecommerce.service.interfaces.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsRepo orderDetailsRepo;
    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepo orderDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
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
    public OrderDetailsRequest saveOrderDetails(OrderDetailsRequest orderDetailsRequest) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUserId(orderDetailsRequest.getUserId());
        orderDetails.setTotal(orderDetailsRequest.getTotal());
        orderDetails.setPaymentId(orderDetailsRequest.getPaymentId());

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        orderDetails.setCreatedAt(
                orderDetailsRequest.getCreatedAt() != null ? orderDetailsRequest.getCreatedAt() : currentTimestamp
        );
        orderDetails.setModifiedAt(
                orderDetailsRequest.getModifiedAt() != null ? orderDetailsRequest.getModifiedAt() : currentTimestamp
        );
        orderDetailsRepo.save(orderDetails);
        return orderDetailsRequest;
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
        response.setUserId(orderDetails.getUserId());
        response.setTotal(orderDetails.getTotal());
        response.setPaymentId(orderDetails.getPaymentId());
        response.setCreatedAt(orderDetails.getCreatedAt());
        response.setModifiedAt(orderDetails.getModifiedAt());
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
        orderItemResponse.setCreatedAt(orderItem.getCreatedAt());
        orderItemResponse.setModifiedAt(orderItem.getModifiedAt());
        return  orderItemResponse;
     }
     private PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails) {
         PaymentDetailsResponse response = new PaymentDetailsResponse();
         response.setId(paymentDetails.getId());
         response.setStatus(paymentDetails.getStatus());
         response.setAmount(paymentDetails.getAmount());
         response.setCreatedAt(paymentDetails.getCreatedAt());
         response.setModifiedAt(paymentDetails.getModifiedAt());
         return response;
     }
}

