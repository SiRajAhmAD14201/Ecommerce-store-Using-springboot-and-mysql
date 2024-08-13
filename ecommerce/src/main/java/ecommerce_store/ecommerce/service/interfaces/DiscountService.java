package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.DiscountRequest;
import ecommerce_store.ecommerce.dto.response.DiscountResponse;

import java.util.List;

public interface DiscountService {
    List<DiscountResponse> findAllDiscount();
    DiscountResponse findDiscountById(Long id);
    DiscountRequest saveDiscount(DiscountRequest discountRequest);
    DiscountRequest updateDiscount(Long id,DiscountRequest discountRequest);
    void deleteDiscount(Long id);
}
