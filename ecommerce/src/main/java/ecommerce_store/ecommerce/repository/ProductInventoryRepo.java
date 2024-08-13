package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepo extends JpaRepository<ProductInventory,Long> {
}
