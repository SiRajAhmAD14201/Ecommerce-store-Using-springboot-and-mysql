package ecommerce_store.ecommerce.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<OrderDetails> orders;

    @OneToMany(mappedBy = "user")
    private Set<UserAddress> addresses;

    @OneToMany(mappedBy = "user")
    private Set<UserPayment> payments;

    // getters and setters
}
