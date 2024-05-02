package hybernate.dao;

import hybernate.entity.Customer;
import hybernate.entity.Rent_info;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    //    CRUD
    String saveCustomer(Customer customer);

    Optional<Customer> findCustomerById(Long customerId);

    List<Customer> findAllCustomers();

    String updateCustomerById(Long customerId, Customer newCustomer);

    //    Customer-ди очуруп жатканда. Ижарасы жок Customer-лер очсун, эгерде ижарасы бар
//    болсо checkout датасын текшерсин. Учурдагы датадан мурун болсо rent_info customer-менен чогу
//    очуп кетсин.
    String deleteCustomerById(Long customerId);

//    void assignAddressIdToAgency(Long addressId, Long agencyId);

    //    Customer эки жол менен тузулсун. Биринчисинде озу эле тузулот, экинчисинде customer
//    тузулуп жатканда бир уйду ижарага алып тузулот(rent_info).
    String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId,
                                LocalDate checkIn, LocalDate checkout);

    // Customer уйду ижарага алса болот. Ижарага алып жатканда customer id, house id,
    // agency id жана check in check out жазышы керек.
    String getHouseRent_info(Long customerId, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkOut);
}