package ecommerce_store.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country") // Added @Column annotation for clarity
    private String country;

    @Column(name = "mobile")
    private String mobile;

    @ManyToOne
    @JoinColumn(name = "user_id") // Ensure this matches the foreign key column in your database
    private User user;

    // No need for explicit getters and setters if using Lombok @Data
}
