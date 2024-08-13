package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepo extends JpaRepository<UserPayment,Long> {
}
