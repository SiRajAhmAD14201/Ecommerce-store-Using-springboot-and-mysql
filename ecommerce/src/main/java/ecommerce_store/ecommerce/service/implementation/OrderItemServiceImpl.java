package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.OrderItemRequest;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;
import ecommerce_store.ecommerce.entities.OrderDetails;
import ecommerce_store.ecommerce.entities.OrderItem;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderDetailsRepo;
import ecommerce_store.ecommerce.repository.OrderItemRepo;
import ecommerce_store.ecommerce.repository.ProductRepo;
import ecommerce_store.ecommerce.service.interfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderItemRepo orderItemRepo;
    private final OrderDetailsRepo orderDetailsRepo;
    private final ProductRepo productRepo;
    @Autowired
    public OrderItemServiceImpl(OrderItemRepo orderItemRepo, OrderDetailsRepo orderDetailsRepo, ProductRepo productRepo) {
        this.orderItemRepo = orderItemRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.productRepo = productRepo;
    }



    @Override
    public List<OrderItemResponse> findAllOrderItem() {
        return orderItemRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse findOrderItemById(Long id) {
        return orderItemRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("ReSource not found against this "+id));
    }

    @Override
    public OrderItemRequest saveOrderItem(OrderItemRequest orderItemRequest) {
        OrderItem orderItem=new OrderItem();
orderItem.setQuantity(orderItemRequest.getQuantity());
orderItem.setId(orderItemRequest.getOrderId());
orderItem.setId(orderItemRequest.getProductId());
orderItem.setOrderDetails(orderDetailsRepo.findById(orderItemRequest.getOrderId()).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")));
orderItem.setProduct(productRepo.findById(orderItemRequest.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")));

        // Save the OrderItem entity
        OrderItem savedOrderItem = orderItemRepo.save(orderItem);

        // Convert saved OrderItem entity to OrderItemResponse
        return toRequest(savedOrderItem);
    }

    @Override
    public OrderItemRequest updateOrderItem(Long id, OrderItemRequest orderItemRequest) {
        // Find the existing OrderItem or throw an exception if not found
        OrderItem existingOrderItem = orderItemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found against this " + id));

        // Update the quantity from the request
        existingOrderItem.setQuantity(orderItemRequest.getQuantity());

        // If a Product ID is provided, set it on the OrderItem
        if (orderItemRequest.getProductId() != null) {
            Product product = productRepo.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + orderItemRequest.getProductId()));
            existingOrderItem.setProduct(product);
        }

        // If an OrderDetails ID is provided, set it on the OrderItem
        if (orderItemRequest.getOrderId() != null) {
            OrderDetails orderDetails = orderDetailsRepo.findById(orderItemRequest.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order details not found with ID " + orderItemRequest.getOrderId()));
            existingOrderItem.setOrderDetails(orderDetails);
        }

        // Save the updated OrderItem
        OrderItem updatedOrderItem = orderItemRepo.save(existingOrderItem);

        // Return the updated OrderItemRequest
        return new OrderItemRequest(
                updatedOrderItem.getOrderDetails() != null ? updatedOrderItem.getOrderDetails().getId() : null,
                updatedOrderItem.getProduct() != null ? updatedOrderItem.getProduct().getId() : null,
                updatedOrderItem.getQuantity()
        );
    }

    @Override
    public void deleteOrderItem(Long id) {
        Optional<OrderItem> orderItemOptional = orderItemRepo.findById(id);
        if (orderItemOptional.isPresent()) {
            orderItemRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Resource not found against this id: " + id);
        }
    }

    private OrderItemResponse toResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setOrderId(orderItem.getId());
       response.setProductId(orderItem.getId());
        response.setQuantity(orderItem.getQuantity());

        return response;
    }
  private OrderItemRequest toRequest(OrderItem orderItem){
        OrderItemRequest orderItemRequest= new OrderItemRequest();
      orderItemRequest.setQuantity(orderItem.getQuantity());

       orderItemRequest.setProductId(orderItem.getId());
       orderItemRequest.setOrderId(orderItem.getId());
        return orderItemRequest;
  }

}
