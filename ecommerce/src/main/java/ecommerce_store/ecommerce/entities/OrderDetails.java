package ecommerce_store.ecommerce.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double total;
    private Long paymentId;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "orderDetails")
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "paymentId", insertable = false, updatable = false)
    private PaymentDetails paymentDetails;

    // getters and setters
}