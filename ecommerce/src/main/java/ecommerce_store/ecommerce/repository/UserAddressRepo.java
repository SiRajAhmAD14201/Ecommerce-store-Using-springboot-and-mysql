package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress,Long> {
    List<UserAddress> findByUserId(Long id);
}
