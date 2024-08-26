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
       discountResponse.setDescription(discount.getDescription());
       discountResponse.setDiscountPercent(discount.getDiscountPercent());
       discountResponse.setActive(discount.isActive());

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
        discount.setDescription(discountRequest.getDescription());
        discount.setDiscountPercent(discountRequest.getDiscountPercent());
        discount.setActive(discountRequest.isActive());


        // Save the Discount entity
        discountRepo.save(discount);

        // Convert saved Discount entity to DiscountResponse
        return discountRequest;
    }

    @Override
    public DiscountRequest updateDiscount(Long id, DiscountRequest discountRequest) {
        // Find the existing Discount entity
        Discount existingDiscount = discountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found against ID: " + id));

        // Update the fields of the existing Discount entity with values from DiscountRequest
        existingDiscount.setName(discountRequest.getName());
        existingDiscount.setDescription(discountRequest.getDescription());
        existingDiscount.setDiscountPercent(discountRequest.getDiscountPercent());
        existingDiscount.setActive(discountRequest.isActive());

        // Save the updated Discount entity
        Discount updatedDiscount = discountRepo.save(existingDiscount);

        // Convert updated Discount entity to DiscountRequest DTO and return it
        return new DiscountRequest(
                updatedDiscount.getName(),
                updatedDiscount.getDescription(),
                updatedDiscount.getDiscountPercent(),
                updatedDiscount.isActive()
        );
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
