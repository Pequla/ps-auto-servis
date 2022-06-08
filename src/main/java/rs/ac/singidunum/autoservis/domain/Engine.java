package rs.ac.singidunum.autoservis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "engine")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Engine {

    @Id
    @Column(name = "engine_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    private Integer cc;

    // Power is in KW
    private Integer power;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fuel_id", nullable = false)
    private Fuel fuel;
}
