package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.AppUtils;
import rs.ac.singidunum.autoservis.domain.*;
import rs.ac.singidunum.autoservis.model.CreateOrderItemModel;
import rs.ac.singidunum.autoservis.repository.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepo;
    private final VehicleRepository vehicleRepo;
    private final RepairStatusRepository statusRepo;
    private final WorkOrderItemRepository workOrderItemRepo;
    private final InventoryItemRepository inventoryItemRepo;
    private final UserService userService;

    public List<WorkOrder> getWorkOrderList() {
        log.info("Listing all work orders");
        return workOrderRepo.findAll();
    }

    public List<RepairStatus> getRepairStatusList() {
        log.info("Listing all repair statuses");
        return statusRepo.findAll();
    }

    public List<WorkOrder> getWorkOrderListForVehicle(Integer vehicleId) {
        Optional<Vehicle> optional = vehicleRepo.findById(vehicleId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Vehicle for id " + vehicleId + "not found");
        }
        Vehicle vehicle = optional.get();
        log.info("Listing all work orders for vehicle " + vehicle.getRegistrationNumber());
        return workOrderRepo.findAllByVehicle(vehicle);
    }

    // PUBLIC ENDPOINT FOR VEHICLE STATUS
    public List<WorkOrder> getAllOrdersForVehicleVin(String vin) {
        Optional<Vehicle> optional = vehicleRepo.findByVinIgnoreCase(vin);
        if (optional.isEmpty()) throw new RuntimeException("Could not find vehicle for vin " + vin);
        return workOrderRepo.findAllByVehicle(optional.get());
    }

    public Optional<WorkOrder> getWorkOrderForId(Integer id) {
        log.info("Searching for work order for id " + id);
        return workOrderRepo.findById(id);
    }

    public WorkOrder saveWorkOrder(Principal principal, WorkOrder workOrder) {
        workOrder.setId(null);
        workOrder.setUser(userService.getUserFromPrincipal(principal));
        workOrder.setStatus(getRepairStatusForName(workOrder.getStatus().getName()));
        log.info("Saving work order");
        return workOrderRepo.save(workOrder);
    }

    private RepairStatus getRepairStatusForName(String name) {
        Optional<RepairStatus> optional = statusRepo.findByName(name);
        log.info("Searching repair status for name " + name);
        if (optional.isEmpty()) {
            throw new RuntimeException("No repair status found for name " + name);
        }
        return optional.get();
    }

    public void addItemToWorkOrder(Principal principal, CreateOrderItemModel model) {
        // Retrieving work order
        Optional<WorkOrder> optional = workOrderRepo.findById(model.getOrderId());
        if (optional.isEmpty()) throw new RuntimeException("Work order not found");
        WorkOrder order = optional.get();

        // Checking if user created the original work order
        AppUser user = userService.getUserFromPrincipal(principal);
        if (order.getUser().getId().equals(user.getId()) ||
                user.getRoles().stream().anyMatch(role -> role.getName().equals(AppUtils.ADMIN_ROLE))) {

            // Setting the inventory item
            Optional<InventoryItem> inventoryOptional = inventoryItemRepo.findById(model.getItemId());
            if (inventoryOptional.isEmpty()) throw new RuntimeException("Inventory item not found");
            InventoryItem invItem = inventoryOptional.get();

            // Check if its in stock
            if (invItem.getAvailable() != null) {
                if (model.getAmount() > invItem.getAvailable())
                    throw new RuntimeException("Item " + invItem.getName() + " has only " + invItem.getAvailable() + " in stock");

                invItem.setAvailable(invItem.getAvailable() - model.getAmount());
            }

            // Constructing object
            WorkOrderItem orderItem = new WorkOrderItem();
            order.setUpdatedAt(LocalDateTime.now());
            orderItem.setOrder(order);
            orderItem.setItem(invItem);
            orderItem.setAmount(model.getAmount());
            orderItem.setDiscount(model.getDiscount());

            workOrderItemRepo.save(orderItem);
            log.info("Successfully added work order item");
        } else
            throw new RuntimeException(user.getEmail() + " can only change this work order");
    }

    public void removeItemFromWorkOrder(Principal principal, Integer workOrderItemId) {
        Optional<WorkOrderItem> orderItemOptional = workOrderItemRepo.findById(workOrderItemId);
        if (orderItemOptional.isEmpty()) {
            throw new RuntimeException("Failed to find work order item for id " + workOrderItemId);
        }
        WorkOrderItem orderItem = orderItemOptional.get();

        AppUser user = userService.getUserFromPrincipal(principal);
        WorkOrder order = orderItem.getOrder();
        if (order.getUser().getId().equals(user.getId()) ||
                user.getRoles().stream().anyMatch(role -> role.getName().equals(AppUtils.ADMIN_ROLE))) {
            // Returning the stock
            InventoryItem item = orderItem.getItem();
            if (item.getAvailable() != null) {
                item.setAvailable(item.getAvailable() + orderItem.getAmount());
                inventoryItemRepo.save(item);
            }

            // Updating order
            order.setUpdatedAt(LocalDateTime.now());
            workOrderRepo.save(order);

            // Removing the order item
            workOrderItemRepo.delete(orderItem);
            log.info("Successfully removed work order item");
        } else
            throw new RuntimeException(user.getEmail() + " can only change this work order");
    }

    public WorkOrder updateWorkOrder(Principal principal, Integer id, WorkOrder order) {
        AppUser user = userService.getUserFromPrincipal(principal);
        log.info("Updating work order");
        if (!order.getUser().getId().equals(user.getId()) ||
                user.getRoles().stream().noneMatch(role -> role.getName().equals(AppUtils.ADMIN_ROLE))) {
            throw new RuntimeException(user.getEmail() + " can only change this work order");
        }
        order.setId(id);
        order.setStatus(getRepairStatusForName(order.getStatus().getName()));
        order.setUpdatedAt(LocalDateTime.now());
        return workOrderRepo.save(order);
    }

    public void deleteWorkOrder(Integer orderId) {
        log.info("Deleting work order for id " + orderId);
        workOrderRepo.deleteById(orderId);
    }
}
