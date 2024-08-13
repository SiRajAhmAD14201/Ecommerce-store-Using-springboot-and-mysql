package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {
}
