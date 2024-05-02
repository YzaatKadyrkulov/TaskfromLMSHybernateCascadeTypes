package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.CustomerDao;
import hybernate.entity.Agency;
import hybernate.entity.Customer;
import hybernate.entity.House;
import hybernate.entity.Rent_info;
import hybernate.enums.FamilyStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {
    private final EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public String saveCustomer(Customer customer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(customer);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
        return "Customer successfully saved";
    }

    @Override
    public Optional<Customer> findCustomerById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = null;
        try {
            entityManager.getTransaction().begin();
            customer = entityManager.find(Customer.class, customerId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Customer> customers = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();
            customers = entityManager.createQuery("select c from Customer c", Customer.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return customers;
    }

    @Override
    public String updateCustomerById(Long customerId, Customer newCustomer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Customer updateCustomerId = entityManager.find(Customer.class, customerId);
            if (updateCustomerId != null) {
                updateCustomerId.setFirstName(newCustomer.getFirstName());
                updateCustomerId.setLastName(newCustomer.getLastName());
                updateCustomerId.setEmail(newCustomer.getEmail());
                updateCustomerId.setDateOfBirth(newCustomer.getDateOfBirth());
                updateCustomerId.setGender(newCustomer.getGender());
                updateCustomerId.setNationality(String.valueOf(newCustomer.getNationality()));
                updateCustomerId.setFamilyStatus(FamilyStatus.valueOf(String.valueOf(newCustomer.getFamilyStatus())));
                entityManager.getTransaction().commit();
                return "Customer with: " + customerId + " successfully updated";
            } else {
                return "Customer with: " + customerId + " not found";
            }
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public String deleteCustomerById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            for (Rent_info rentInfo : customer.getRentInfoList()) {
                if (rentInfo != null) {
                    if (!rentInfo.getCheckOut().isBefore(LocalDate.now())) {
                        throw new RuntimeException("error");
                    }
                }
//                assert rentInfo != null;
                rentInfo.getAgency().getRentInfos().remove(rentInfo);
                rentInfo.getHouse().setRentInfo(null);
            }
            entityManager.remove(customer);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
        return " Successfully removed";
    }

    @Override
    public String saveCustomerWithRent(Customer newCustomer, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkout) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, agencyId);
            House house = entityManager.find(House.class, houseId);

            if (checkHouseAble(entityManager, houseId, checkIn, checkout)) {
                return "There are no houses for the selected dates";
            }
            Rent_info rentInfo = new Rent_info();
            rentInfo.setCustomer(newCustomer);
            rentInfo.setHouse(house);
            rentInfo.setAgency(agency);
            rentInfo.setCheckIn(checkIn);
            rentInfo.setCheckOut(checkout);


            house.setRentInfo(rentInfo);
            agency.getRentInfos().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.persist(newCustomer);

            if (newCustomer.getRentInfoList() == null) {
                newCustomer.setRentInfoList(new ArrayList<>());
            }
            newCustomer.getRentInfoList().add(rentInfo);

            entityManager.persist(newCustomer);
            entityManager.persist(rentInfo);
            entityManager.getTransaction().commit();

            return newCustomer.getFirstName() + " Successfully saved!!!";
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
    }


    // Customer уйду ижарага алса болот. Ижарага алып жатканда customer id, house id,
    // agency id жана check in check out жазышы керек.
    @Override
    public String getHouseRent_info(Long customerId, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkOut) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, agencyId);
            House house = entityManager.find(House.class, houseId);
            Customer customer = entityManager.find(Customer.class, customerId);

            boolean houseAble = !checkHouseAble(entityManager, houseId, checkIn, checkOut);

            if (!houseAble) {
                return "There are no houses for the selected dates";
            }
            Rent_info rentInfo = new Rent_info();
            rentInfo.setCustomer(customer);
            rentInfo.setHouse(house);
            rentInfo.setAgency(agency);
            rentInfo.setCheckIn(checkIn);
            rentInfo.setCheckOut(checkOut);

            house.setRentInfo(rentInfo);
            agency.getRentInfos().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.persist(customer);

            if (customer.getRentInfoList() == null) {
                customer.setRentInfoList(new ArrayList<>());
            }
            customer.getRentInfoList().add(rentInfo);
            house.setRentInfo(rentInfo);
            agency.getRentInfos().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.getTransaction().commit();

            return "House successfully rented by customer!";
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
    }

    //    Customer-ди очуруп жатканда. Ижарасы жок Customer-лер очсун, эгерде ижарасы бар
//    болсо checkout датасын текшерсин. Учурдагы датадан мурун болсо rent_info customer-менен чогу
//    очуп кетсин.

    public void rollBack(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }

    private boolean checkHouseAble(EntityManager entityManager, Long houseId,
                                   LocalDate checkIn, LocalDate checkout) {
        String jpql = "select count(distinct r.house.id) from Rent_info r " +
                "where r.house.id = :houseId " +
                "and (:checkIn between r.checkIn and r.checkIn " +
                "or :checkout between r.checkIn and r.checkOut)";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("houseId", houseId)
                .setParameter("checkIn", checkIn)
                .setParameter("checkout", checkout)
                .getSingleResult();

        return count != 0;
    }
}

