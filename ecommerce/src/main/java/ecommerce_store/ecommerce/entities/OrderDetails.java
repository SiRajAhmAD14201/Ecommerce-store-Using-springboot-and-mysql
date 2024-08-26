package ecommerce_store.ecommerce.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details") // Optional: specify the table name if different from the default
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify column name explicitly
    private Long id;

    @Column(name = "total") // Optional: specify column name explicitly
    private double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentDetails paymentDetails;

    // No need for explicit getters and setters if using Lombok @Data
}
