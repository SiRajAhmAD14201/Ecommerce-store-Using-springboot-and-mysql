package ecommerce_store.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_payment") // Optional: specify the table name if different from the default
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_type") // Optional: specify column names for consistency
    private String paymentType;

    @Column(name = "provider")
    private String provider;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "expiry")
    private Date expiry;

    @ManyToOne
    @JoinColumn(name = "user_id") // Ensure this matches the foreign key column in your database
    private User user;

    // No need for explicit getters and setters if using Lombok @Data
}