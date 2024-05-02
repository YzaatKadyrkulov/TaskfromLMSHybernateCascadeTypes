package hybernate.service.impl;

import hybernate.dao.CustomerDao;
import hybernate.dao.impl.CustomerDaoImpl;
import hybernate.entity.Customer;
import hybernate.service.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public String saveCustomer(Customer customer) {
        return customerDao.saveCustomer(customer);
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        return customerDao.findCustomerById(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAllCustomers();
    }

    @Override
    public String updateCustomerById(Long customerId, Customer newCustomer) {
        return customerDao.updateCustomerById(customerId, newCustomer);
    }

    @Override
    public String deleteCustomerById(Long customerId) {
        return customerDao.deleteCustomerById(customerId);
    }

    @Override
    public String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkout) {
        return customerDao.saveCustomerWithRent(newCustomer, houseId, agencyId, checkIn, checkout);
    }

    @Override
    public String getHouseRent_info(Long customerId, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkOut) {
        return customerDao.getHouseRent_info(customerId, houseId, agencyId, checkIn, checkOut);
    }
}

