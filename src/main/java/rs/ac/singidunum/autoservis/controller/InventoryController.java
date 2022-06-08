package rs.ac.singidunum.autoservis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.autoservis.domain.InventoryItem;
import rs.ac.singidunum.autoservis.service.InventoryService;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/inventory")
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public List<InventoryItem> getAll() {
        return service.getInventoryItemList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InventoryItem> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getInventoryItemById(id));
    }

    @PostMapping
    public InventoryItem save(@RequestBody InventoryItem item) {
        return service.saveInventoryItem(item);
    }

    @PutMapping(path = "/{id}")
    public InventoryItem update(@RequestBody InventoryItem item, @PathVariable Integer id) {
        return service.updateInventoryItem(id, item);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteInventoryItem(id);
    }
}
