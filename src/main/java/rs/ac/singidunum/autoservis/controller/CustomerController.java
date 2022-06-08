package rs.ac.singidunum.autoservis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.autoservis.domain.Customer;
import rs.ac.singidunum.autoservis.service.CustomerService;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(path = "/api/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public List<Customer> getAll() {
        return service.getAllCustomers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.findByCustomerId(id));
    }

    @GetMapping(path = "/name")
    public List<Customer> getAllFromName(@RequestParam(name = "q") String name) {
        return service.findByCustomerName(name);
    }

    @GetMapping(path = "/idnumber")
    public ResponseEntity<Customer> getFromIdNumber(@RequestParam(name = "q") Long idNumber) {
        return ResponseEntity.of(service.findByIdNumber(idNumber));
    }

    @GetMapping(path = "/taxid")
    public ResponseEntity<Customer> getFromTaxId(@RequestParam(name = "q") Long taxId) {
        return ResponseEntity.of(service.findByTaxId(taxId));
    }

    @PostMapping
    public Customer save(@RequestBody Customer customer) {
        return service.saveCustomer(customer);
    }

    @PutMapping(path = "/{id}")
    public Customer update(@PathVariable Integer id, @RequestBody Customer customer) {
        return service.updateCustomer(id, customer);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteCustomer(id);
    }
}
