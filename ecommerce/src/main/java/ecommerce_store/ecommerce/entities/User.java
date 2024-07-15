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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private Timestamp createdAt;
    private Timestamp modifiedAt;

    @OneToMany(mappedBy = "user")
    private Set<OrderDetails> orders;

    @OneToMany(mappedBy = "user")
    private Set<UserAddress> addresses;

    @OneToMany(mappedBy = "user")
    private Set<UserPayment> payments;

    // getters and setters
}
