package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.OrderItemRequest;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;
import ecommerce_store.ecommerce.entities.OrderItem;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.OrderItemRepo;
import ecommerce_store.ecommerce.service.interfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {


    private final OrderItemRepo orderItemRepo;
    @Autowired
    public OrderItemServiceImpl(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
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
orderItem.setOrderId(orderItemRequest.getOrderId());
orderItem.setProductId(orderItemRequest.getProductId());

        // Save the OrderItem entity
        OrderItem savedOrderItem = orderItemRepo.save(orderItem);

        // Convert saved OrderItem entity to OrderItemResponse
        return toRequest(savedOrderItem);
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
        response.setOrderId(orderItem.getOrderId());
        response.setProductId(orderItem.getProductId());
        response.setQuantity(orderItem.getQuantity());
        response.setCreatedAt(orderItem.getCreatedAt());
        response.setModifiedAt(orderItem.getModifiedAt());
        return response;
    }
  private OrderItemRequest toRequest(OrderItem orderItem){
        OrderItemRequest orderItemRequest= new OrderItemRequest();
        orderItemRequest.setProductId(orderItem.getProductId());
        orderItemRequest.setQuantity(orderItem.getQuantity());
        orderItemRequest.setOrderId(orderItem.getOrderId());
        return orderItemRequest;
  }

}
