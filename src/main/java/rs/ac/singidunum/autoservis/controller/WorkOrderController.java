package rs.ac.singidunum.autoservis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.autoservis.domain.RepairStatus;
import rs.ac.singidunum.autoservis.domain.WorkOrder;
import rs.ac.singidunum.autoservis.domain.WorkOrderItem;
import rs.ac.singidunum.autoservis.model.CreateOrderItemModel;
import rs.ac.singidunum.autoservis.service.WorkOrderService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/order")
public class WorkOrderController {

    private final WorkOrderService service;

    @GetMapping
    public List<WorkOrder> getAll() {
        return service.getWorkOrderList();
    }

    @GetMapping(path = "/vehicle/{id}")
    public List<WorkOrder> getAllForVehicle(@PathVariable Integer id) {
        return service.getWorkOrderListForVehicle(id);
    }

    @GetMapping(path = "/vehicle/vin/{vin}")
    public List<WorkOrder> getAllForVehicle(@PathVariable String vin) {
        return service.getAllOrdersForVehicleVin(vin);
    }

    @GetMapping(path = "/status")
    public List<RepairStatus> getAllRepairStatus() {
        return service.getRepairStatusList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<WorkOrder> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getWorkOrderForId(id));
    }

    @PostMapping
    public WorkOrder save(Principal principal, @RequestBody WorkOrder order) {
        return service.saveWorkOrder(principal, order);
    }

    @PostMapping("/item")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveItem(Principal principal, @RequestBody CreateOrderItemModel model) {
        service.addItemToWorkOrder(principal, model);
    }

    @PutMapping(path = "/{id}")
    public WorkOrder update(Principal principal, @PathVariable Integer id, @RequestBody WorkOrder order) {
        return service.updateWorkOrder(principal, id, order);
    }

    @DeleteMapping("/item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(Principal principal, @PathVariable Integer id) {
        service.removeItemFromWorkOrder(principal, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteWorkOrder(id);
    }
}
