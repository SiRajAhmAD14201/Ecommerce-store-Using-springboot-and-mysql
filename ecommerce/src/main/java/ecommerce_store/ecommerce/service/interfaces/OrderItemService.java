package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.OrderItemRequest;
import ecommerce_store.ecommerce.dto.response.OrderItemResponse;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> findAllOrderItem();
    OrderItemResponse findOrderItemById(Long id);
    OrderItemRequest saveOrderItem(OrderItemRequest orderItemRequest);
    OrderItemRequest updateOrderItem(Long id,OrderItemRequest orderItemRequest);
    void deleteOrderItem(Long id);
}
