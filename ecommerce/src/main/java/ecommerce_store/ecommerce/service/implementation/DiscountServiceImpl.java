package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.DiscountRequest;
import ecommerce_store.ecommerce.dto.response.DiscountResponse;
import ecommerce_store.ecommerce.entities.Discount;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.DiscountRepo;
import ecommerce_store.ecommerce.service.interfaces.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepo discountRepo;
@Autowired
    public DiscountServiceImpl(DiscountRepo discountRepo) {
        this.discountRepo = discountRepo;
    }

    @Override
    public List<DiscountResponse> findAllDiscount() {
     return discountRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    private DiscountResponse toResponse(Discount discount){
       DiscountResponse discountResponse=new DiscountResponse();
       discountResponse.setId(discount.getId());
       discountResponse.setName(discount.getName());
       discountResponse.setDesc(discount.getDesc());
       discountResponse.setDiscountPercent(discount.getDiscountPercent());
       discountResponse.setActive(discount.isActive());
       discountResponse.setCreatedAt(discount.getCreatedAt());
       discountResponse.setModifiedAt(discount.getModifiedAt());
       discountResponse.setDeletedAt(discount.getDeletedAt());
       return discountResponse;
    }

    @Override
    public DiscountResponse findDiscountById(Long id) {
        return discountRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("Recourse not found against this id"+id));
    }

    @Override
    public DiscountRequest saveDiscount(DiscountRequest discountRequest) {
        // Convert DiscountRequest to Discount entity
        Discount discount = new Discount();
        discount.setName(discountRequest.getName());
        discount.setDesc(discountRequest.getDesc());
        discount.setDiscountPercent(discountRequest.getDiscountPercent());
        discount.setActive(discountRequest.isActive());

        // Set timestamps or use current time if not provided
        discount.setCreatedAt(
                discountRequest.getCreatedAt() != null ? discountRequest.getCreatedAt() : new Timestamp(System.currentTimeMillis())
        );
        discount.setModifiedAt(
                discountRequest.getModifiedAt() != null ? discountRequest.getModifiedAt() : new Timestamp(System.currentTimeMillis())
        );
        discount.setDeletedAt(discountRequest.getDeletedAt());

        // Save the Discount entity
        discountRepo.save(discount);

        // Convert saved Discount entity to DiscountResponse
        return discountRequest;
    }

    @Override
    public DiscountRequest updateDiscount(Long id, DiscountRequest discountRequest) {
        return null;
    }

    @Override
    public void deleteDiscount(Long id) {
        // Check if Discount exists
        Optional<Discount> discountOptional = discountRepo.findById(id);
        if (discountOptional.isPresent()) {
            discountRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Resource not found against this id: " + id);
        }
    }

}
