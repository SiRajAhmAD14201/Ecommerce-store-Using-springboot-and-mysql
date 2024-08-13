package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Long> {
}
