package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByDiscountId(Long id);
    Set<Product> findByCategoryId(Long categoryId);
    Set<Product> findByInventoryId(Long inventoryId);
}
