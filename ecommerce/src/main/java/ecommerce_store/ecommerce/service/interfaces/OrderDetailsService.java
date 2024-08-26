package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.OrderDetailsRequest;
import ecommerce_store.ecommerce.dto.response.OrderDetailsResponse;

import java.util.List;

public interface OrderDetailsService {
    List<OrderDetailsResponse> findAllOrderDetails();
    OrderDetailsResponse findOrderDetailsById(Long id);
    OrderDetailsRequest saveOrderDetails(OrderDetailsRequest orderDetailsRequest);
    OrderDetailsRequest updateOrderDetails(Long id,OrderDetailsRequest orderDetailsRequest);
    void deleteOrderDetails(Long id);

}
