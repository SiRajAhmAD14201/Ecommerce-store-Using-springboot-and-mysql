package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepo extends JpaRepository<UserAddress,Long> {
}
