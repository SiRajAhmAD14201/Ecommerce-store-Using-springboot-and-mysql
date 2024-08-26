package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.CartItemRequest;
import ecommerce_store.ecommerce.dto.response.CartItemResponse;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> findAllCartItem();
    CartItemResponse findCartItemById(Long id);
    CartItemRequest saveCartItem(CartItemRequest cartItemRequest);
    void deleteCartItem(Long id);
    CartItemRequest updateCartItem(Long id,CartItemRequest cartItemRequest);
}
