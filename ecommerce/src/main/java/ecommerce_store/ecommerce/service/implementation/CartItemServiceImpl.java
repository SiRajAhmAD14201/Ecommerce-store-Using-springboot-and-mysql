package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.CartItemRequest;
import ecommerce_store.ecommerce.dto.response.CartItemResponse;
import ecommerce_store.ecommerce.entities.CartItem;
import ecommerce_store.ecommerce.entities.Product;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.CartItemRepo;
import ecommerce_store.ecommerce.repository.ProductRepo;
import ecommerce_store.ecommerce.service.interfaces.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CartItemServiceImpl(CartItemRepo cartItemRepo, ProductRepo productRepo) {
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
    }

    @Override
    public List<CartItemResponse> findAllCartItem() {
        return cartItemRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponse findCartItemById(Long id) {
        return cartItemRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id " + id));
    }

    @Override
    public CartItemRequest saveCartItem(CartItemRequest cartItemRequest) {
        CartItem cartItem = toEntity(cartItemRequest);
        CartItem savedCartItem = cartItemRepo.save(cartItem);
        return toRequest(savedCartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        if (!cartItemRepo.existsById(id)) {
            throw new ResourceNotFoundException("CartItem not found with id: " + id);
        }
        cartItemRepo.deleteById(id);
    }

    @Override
    public CartItemRequest updateCartItem(Long id, CartItemRequest cartItemRequest) {
        CartItem updateCartItem = cartItemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id " + id));

        updateCartItem.setQuantity(cartItemRequest.getQuantity());

        if (cartItemRequest.getProductId() != null) {
            Product product = productRepo.findById(cartItemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + cartItemRequest.getProductId()));
            updateCartItem.setProduct(product);
        }

        CartItem updatedCartItem = cartItemRepo.save(updateCartItem);

        return new CartItemRequest(
                updatedCartItem.getProduct() != null ? updatedCartItem.getProduct().getId() : null,
                updatedCartItem.getQuantity()
        );
    }

    private CartItem toEntity(CartItemRequest cartItemRequest) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartItemRequest.getQuantity());

        if (cartItemRequest.getProductId() != null) {
            Product product = productRepo.findById(cartItemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + cartItemRequest.getProductId()));
            cartItem.setProduct(product);
        }

        return cartItem;
    }

    private CartItemRequest toRequest(CartItem cartItem) {
        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setProductId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null);
        cartItemRequest.setQuantity(cartItem.getQuantity());


        return cartItemRequest;
    }

    private CartItemResponse toResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setId(cartItem.getId());
        cartItemResponse.setProductId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null);
        cartItemResponse.setQuantity(cartItem.getQuantity());

        return cartItemResponse;
    }
}
