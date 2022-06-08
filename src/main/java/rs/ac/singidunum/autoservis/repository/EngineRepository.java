package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.Engine;
import rs.ac.singidunum.autoservis.domain.Fuel;
import rs.ac.singidunum.autoservis.domain.Manufacturer;

import java.util.List;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Integer> {

    List<Engine> findAllByManufacturer(Manufacturer manufacturer);

    List<Engine> findAllByFuel(Fuel fuel);
}
