package rs.ac.singidunum.autoservis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InventoryItem {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String oem;

    private Double available;

    @Column(nullable = false)
    private Double price;
}
