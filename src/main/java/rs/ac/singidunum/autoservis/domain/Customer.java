package rs.ac.singidunum.autoservis.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "customer")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    /*
     * This field represents:
     * 1) JMBG if the customer is an individual
     * 2) Company Number if the customer is a legal entity
     */
    @Column(name = "id_number", nullable = false, unique = true)
    private Long identificationNumber;

    // NULL if the customer is an individual, only for legal entities
    private Long taxId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
