package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.RepairStatus;

@Repository
public interface RepairStatusRepository extends CrudRepository<RepairStatus, Integer> {
}
