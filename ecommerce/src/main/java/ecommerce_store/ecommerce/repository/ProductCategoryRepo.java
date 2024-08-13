package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory,Long> {
}
