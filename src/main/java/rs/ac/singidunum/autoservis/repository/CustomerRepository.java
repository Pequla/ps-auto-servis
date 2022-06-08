package rs.ac.singidunum.autoservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.autoservis.domain.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByNameContainsIgnoreCase(String name);

    Optional<Customer> findByIdentificationNumber(Long identificationNumber);

    Optional<Customer> findByTaxId(Long taxId);
}
