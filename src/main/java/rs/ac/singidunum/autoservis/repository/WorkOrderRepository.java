package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.Vehicle;
import rs.ac.singidunum.autoservis.domain.WorkOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Integer> {

    List<WorkOrder> findAllByVehicle(Vehicle vehicle);
}
