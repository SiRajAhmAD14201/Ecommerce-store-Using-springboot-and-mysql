package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepo extends JpaRepository<UserPayment,Long> {
}
