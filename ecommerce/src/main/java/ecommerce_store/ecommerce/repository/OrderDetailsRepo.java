package ecommerce_store.ecommerce.repository;

import ecommerce_store.ecommerce.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findByUserId(Long id);
    @Query("SELECT od FROM OrderDetails od " +
            "JOIN FETCH od.user u " +
            "LEFT JOIN FETCH od.orderItems oi " +
            "WHERE od.id = :id")
    Optional<OrderDetails> findByIdWithUserAndOrderItems(@Param("id") Long id);
}
