package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.InventoryItem;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
}