package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.autoservis.domain.Customer;
import rs.ac.singidunum.autoservis.repository.CustomerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<Customer> getAllCustomers() {
        log.info("Listing all customers");
        return repository.findAll();
    }

    public List<Customer> findByCustomerName(String name) {
        log.info("Listing customers for query '" + name + "'");
        return repository.findByNameContainsIgnoreCase(name);
    }

    public Optional<Customer> findByCustomerId(Integer id) {
        log.info("Searching for customer with id " + id);
        return repository.findById(id);
    }

    public Optional<Customer> findByTaxId(Long taxId) {
        log.info("Searching for customer with tax id " + taxId);
        return repository.findByTaxId(taxId);
    }

    public Optional<Customer> findByIdNumber(Long identificationNumber) {
        log.info("Searching for customer with idNumber " + identificationNumber);
        return repository.findByIdentificationNumber(identificationNumber);
    }

    public Customer saveCustomer(Customer customer) {
        customer.setId(null);

        log.info("Saving customer " + customer);
        return repository.save(customer);
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        customer.setId(id);
        customer.setUpdatedAt(LocalDateTime.now());

        log.info("Updating customer " + customer);
        return repository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        log.info("Deleting customer for id " + id);
        repository.deleteById(id);
    }
}
