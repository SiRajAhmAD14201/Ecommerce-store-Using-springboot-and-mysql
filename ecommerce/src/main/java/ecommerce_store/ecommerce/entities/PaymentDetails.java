package ecommerce_store.ecommerce.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_details") // Optional: specify the table name if different from the default
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional: specify column name explicitly
    private Long id;

    @Column(name = "amount") // Optional: specify column name explicitly
    private double amount;

    @Column(name = "provider") // Optional: specify column name explicitly
    private String provider;

    @Column(name = "status") // Optional: specify column name explicitly
    private String status;

    // No need for explicit getters and setters if using Lombok @Data
}