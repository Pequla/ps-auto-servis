package rs.ac.singidunum.autoservis.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.autoservis.domain.*;
import rs.ac.singidunum.autoservis.service.VehicleService;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping(path = "/api/vehicle")
public class VehicleController {

    private final VehicleService service;

    @GetMapping
    public List<Vehicle> getVehicleList() {
        return service.getAllVehicles();
    }

    @GetMapping(path = "/model")
    public List<VehicleModel> getVehicleModelList() {
        return service.getAllVehicleModels();
    }

    @GetMapping(path = "/engine")
    public List<Engine> getEngineList() {
        return service.getAllEngines();
    }

    @GetMapping(path = "/transmission")
    public List<Transmission> getTransmissionList() {
        return service.findAllTransmission();
    }

    @GetMapping(path = "/manufacturer")
    public List<Manufacturer> getManufacturerList() {
        return service.findAllManufacturers();
    }

    @GetMapping(path = "/fuel")
    public List<Fuel> getFuelList() {
        return service.findAllFuelTypes();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getVehicleForId(id));
    }

    @GetMapping(path = "/vin/{vin}")
    public ResponseEntity<Vehicle> getVehicleByVin(@PathVariable String vin) {
        return ResponseEntity.of(service.getVehicleByVin(vin));
    }

    @GetMapping(path = "/customer/{id}")
    public List<Vehicle> getAllVehiclesByCustomer(@PathVariable Integer id) {
        return service.getAllVehiclesByCustomer(id);
    }

    @PostMapping
    public Vehicle saveVehicle(@RequestBody Vehicle vehicle) {
        return service.saveVehicle(vehicle);
    }

    @PutMapping(path = "/{id}")
    public Vehicle updateVehicle(@PathVariable Integer id, @RequestBody Vehicle vehicle) {
        return service.updateVehicle(id, vehicle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Integer id) {
        service.deleteVehicle(id);
    }
}
