package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {

}
