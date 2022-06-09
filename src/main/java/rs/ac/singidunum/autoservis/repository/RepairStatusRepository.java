package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.RepairStatus;

import java.util.Optional;

@Repository
public interface RepairStatusRepository extends JpaRepository<RepairStatus, Integer> {

    Optional<RepairStatus> findByName(String name);
}
