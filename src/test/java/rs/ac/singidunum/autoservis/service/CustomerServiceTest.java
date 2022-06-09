package rs.ac.singidunum.autoservis.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import rs.ac.singidunum.autoservis.domain.Customer;
import rs.ac.singidunum.autoservis.repository.CustomerRepository;

import java.util.List;

@RequiredArgsConstructor
class CustomerServiceTest {

    private final CustomerRepository repository;

    // INTEGRATION TEST

    @Test
    void findByCustomerName() {
        CustomerService service = new CustomerService(repository);
        List<Customer> response = service.findByCustomerName("Pera Peric");
        Assertions.assertEquals(1, response.size());
    }
}