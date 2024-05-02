package hybernate.service;

import hybernate.entity.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    //    CRUD
    String saveCustomer(Customer customer);

    Optional<Customer> findCustomerById(Long customerId);

    List<Customer> findAllCustomers();

    String updateCustomerById(Long customerId, Customer newCustomer);

    String deleteCustomerById(Long customerId);

    String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId,
                                LocalDate checkIn, LocalDate checkout);

    String getHouseRent_info(Long customerId, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkOut);
}
