package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepo extends JpaRepository<Discount,Long> {
}
