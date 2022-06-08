package rs.ac.singidunum.autoservis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "model")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleModel {

    @Id
    @Column(name = "model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "engine_id", nullable = false)
    private Engine engine;

    @ManyToOne(optional = false)
    @JoinColumn(name = "transmission_id", nullable = false)
    private Transmission transmission;

    private Integer gearCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;
}
