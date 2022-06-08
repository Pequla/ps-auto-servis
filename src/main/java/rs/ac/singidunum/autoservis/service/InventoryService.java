package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.domain.InventoryItem;
import rs.ac.singidunum.autoservis.repository.InventoryItemRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository repository;

    public List<InventoryItem> getInventoryItemList() {
        log.info("Listing all inventory items");
        return repository.findAll();
    }

    public Optional<InventoryItem> getInventoryItemById(Integer id) {
        log.info("Searching for inventory item with id " + id);
        return repository.findById(id);
    }

    public InventoryItem saveInventoryItem(InventoryItem item) {
        item.setId(null);
        log.info("Saving inventory item " + item.getName());
        return repository.save(item);
    }

    public InventoryItem updateInventoryItem(Integer id, InventoryItem item) {
        item.setId(id);
        log.info("Updating inventory item with id " + id);
        return repository.save(item);
    }

    public void deleteInventoryItem(Integer id) {
        repository.deleteById(id);
    }
}
