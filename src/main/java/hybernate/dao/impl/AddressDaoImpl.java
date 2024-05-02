package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.AddressDao;
import hybernate.entity.Address;
import hybernate.entity.Agency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.*;

public class AddressDaoImpl implements AddressDao {
    private final EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public Optional<Address> findAddressById(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            address = entityManager.find(Address.class, addressId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(address);
    }

    @Override
    public List<Address> findAllAddresses() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Address> addresses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            addresses = entityManager.createQuery("select a from Address a", Address.class).
                    getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return addresses;
    }

    @Override
    public String updateAddressById(Long addressId, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = entityManager.find(Address.class, addressId);
        try {
            entityManager.getTransaction().begin();
            if (address != null) {
                address.setCity(newAddress.getCity());
                address.setRegion(newAddress.getRegion());
                address.setStreet(newAddress.getStreet());
                entityManager.getTransaction().commit();
                return "Agency with ID " + addressId + " updated successfully";
            } else {
                return "Agency with ID " + addressId + " not found";
            }
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
    }

    //Бир метод ар бир адрести агентсвосу менен чыгарсын.
    @Override
    public Map<Address, Agency> outputAllAddressWithAgency() {
        Map<Address, Agency> addressAgencyMap = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Agency> results = entityManager.createQuery("select a from Agency a", Agency.class)
                    .getResultList();
            for (Agency agency : results) {
                addressAgencyMap.put(agency.getAddress(), agency);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return addressAgencyMap;
    }

    //  Колдонуучу бир шаардын атын жазса ошол шаарда канча агентсво бар экенин эсептеп чыгарсын
    @Override
    public int getCountAgenciesByCityName(String cityName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int count = 0;
        try {
            entityManager.getTransaction().begin();

            count = entityManager.createQuery("select count(a) from Agency a " +
                            "inner join a.address ad where ad.city = : cityName", Long.class)
                    .setParameter("cityName", cityName)
                    .getSingleResult()
                    .intValue();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return count;
    }

    //Ар бир регион жана ошол региондун агентсволары баары чыксын Map<String, List<Agency>> groupByRegion.
    @Override
    public Map<String, List<Agency>> groupByRegion() {
        Map<String, List<Agency>> allAgencyWithAddress = new HashMap<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Object[]> results = entityManager.createQuery("select a.address.region, a from Agency a ",
                            Object[].class)
                    .getResultList();

            for (Object[] result : results) {
                String region = (String) result[0];
                Agency agency = (Agency) result[1];

                List<Agency> agenciesInRegion = allAgencyWithAddress.getOrDefault(region, new ArrayList<>());
                agenciesInRegion.add(agency);
                allAgencyWithAddress.put(region, agenciesInRegion);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
        return allAgencyWithAddress;
    }

    private void rollBack(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }

    }
}