package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.repository.InventoryItemRepository;
import rs.ac.singidunum.autoservis.repository.WorkOrderItemRepository;
import rs.ac.singidunum.autoservis.repository.WorkOrderRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepo;
    private final WorkOrderItemRepository workOrderItemRepo;
    private final InventoryItemRepository inventoryItemRepository;


}
