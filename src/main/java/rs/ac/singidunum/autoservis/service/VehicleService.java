package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.domain.*;
import rs.ac.singidunum.autoservis.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepo;
    private final CustomerRepository customerRepo;
    private final VehicleModelRepository vehicleModelRepo;
    private final EngineRepository engineRepo;
    private final TransmissionRepository transmissionRepo;
    private final ManufacturerRepository manufacturerRepo;
    private final FuelRepository fuelRepo;

    // ====== VEHICLE ======
    public List<Vehicle> getAllVehicles() {
        log.info("Listing all vehicles");
        return vehicleRepo.findAll();
    }

    public List<Vehicle> getAllVehiclesByCustomer(Integer customerId) {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("Customer not found for id " + customerId);
        }
        Customer customer = optionalCustomer.get();
        log.info("Listing all vehicles for customer " + customer.getName());
        return vehicleRepo.findAllByCustomer(customer);
    }

    public Optional<Vehicle> getVehicleForId(Integer id) {
        log.info("Looking up vehicle for id " + id);
        return vehicleRepo.findById(id);
    }

    public Optional<Vehicle> getVehicleByVin(String vin) {
        log.info("Looking up vehicle with vin " + vin);
        return vehicleRepo.findByVinIgnoreCase(vin);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        vehicle.setId(null);
        vehicle.setVin(vehicle.getVin().toUpperCase());
        vehicle.setRegistrationNumber(vehicle.getRegistrationNumber().toUpperCase());
        log.info("Saving vehicle");
        return vehicleRepo.save(vehicle);
    }

    public Vehicle updateVehicle(Integer id, Vehicle vehicle) {
        vehicle.setId(id);
        vehicle.setUpdatedAt(LocalDateTime.now());
        log.info("Updating vehicle for id " + id);
        return vehicleRepo.save(vehicle);
    }

    public void deleteVehicle(Integer id) {
        log.info("Deleting vehicle for id " + id);
        vehicleRepo.deleteById(id);
    }

    // ====== VEHICLE MODEL ======
    public List<VehicleModel> getAllVehicleModels() {
        log.info("Listing all vehicle models");
        return vehicleModelRepo.findAll();
    }

    public Optional<VehicleModel> getVehicleModelById(Integer id) {
        log.info("Searching vehicle model for id " + id);
        return vehicleModelRepo.findById(id);
    }

    public VehicleModel saveVehicleModel(VehicleModel model) {
        model.setId(null);
        log.info("Saving vehicle model");
        return vehicleModelRepo.save(model);
    }

    public VehicleModel updateVehicleModel(Integer id, VehicleModel model) {
        model.setId(id);
        log.info("Updating vehicle model for id " + id);
        return vehicleModelRepo.save(model);
    }

    public void deleteVehicleModel(Integer id) {
        log.info("Deleting vehicle model for id " + id);
        vehicleModelRepo.deleteById(id);
    }

    // ====== ENGINE ======
    public List<Engine> getAllEngines() {
        log.info("Listing all engines");
        return engineRepo.findAll();
    }

    public Optional<Engine> getEngineById(Integer id) {
        log.info("Searching for engine with id " + id);
        return engineRepo.findById(id);
    }

    public List<Engine> getAllEnginesByManufacturer(String name) {
        Optional<Manufacturer> manufacturerOptional = manufacturerRepo.findByNameIgnoreCase(name);
        if (manufacturerOptional.isEmpty()) {
            throw new RuntimeException("No manufacturer found for name " + name);
        }
        Manufacturer manufacturer = manufacturerOptional.get();
        log.info("Listing all engines for manufacturer " + manufacturer.getName());
        return engineRepo.findAllByManufacturer(manufacturer);
    }

    public List<Engine> getAllEnginesByFuel(String name) {
        Optional<Fuel> fuelOptional = fuelRepo.findByNameIgnoreCase(name);
        if (fuelOptional.isEmpty()) {
            throw new RuntimeException("No fuel found for name " + name);
        }
        Fuel fuel = fuelOptional.get();
        log.info("Listing all engines for fuel " + fuel.getName());
        return engineRepo.findAllByFuel(fuel);
    }

    public Engine addEngine(Engine engine) {
        engine.setId(null);
        log.info("Saving an engine");
        return engineRepo.save(engine);
    }

    public Engine updateEngine(Integer id, Engine engine) {
        engine.setId(id);
        log.info("Updating engine for id " + id);
        return engineRepo.save(engine);
    }

    public void deleteEngine(Integer id) {
        log.info("Deleting engine for id " + id);
        engineRepo.deleteById(id);
    }

    /*
        All under are supposed to be static
        Entries should already exist in the database itself
     */

    public List<Transmission> findAllTransmission() {
        log.info("Listing all transmissions");
        return transmissionRepo.findAll();
    }

    public List<Manufacturer> findAllManufacturers() {
        log.info("Listing all manufacturers");
        return manufacturerRepo.findAll();
    }

    public List<Fuel> findAllFuelTypes() {
        log.info("Listing all fuel types");
        return fuelRepo.findAll();
    }
}
