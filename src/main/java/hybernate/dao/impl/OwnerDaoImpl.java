package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.OwnerDao;
import hybernate.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.*;

public class OwnerDaoImpl implements OwnerDao {
    private final EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public String saveOwner(Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(owner);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return owner.getFirstName() + " Successfully saved!!!";
    }


    @Override
    public String saveOwner(Owner owner, House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            owner.addHouse(house);
            house.setOwner(owner);
            entityManager.persist(owner);
            entityManager.persist(house);
            entityManager.getTransaction().commit();
            return owner.getFirstName() + " Successfully saved!!!";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public Optional<Owner> findOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner owner = null;
        try {
            entityManager.getTransaction().begin();
            owner = entityManager.createQuery("select o from Owner o where id =:ownerId", Owner.class)
                    .setParameter("ownerId", ownerId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(owner);
    }


    @Override
    public List<Owner> findAllOwners() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o", Owner.class)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return owners;
    }

    @Override
    public String updateOwnerById(Long ownerId, Owner newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            findOwner.setFirstName(newOwner.getFirstName());
            findOwner.setLastName(newOwner.getLastName());
            findOwner.setEmail(newOwner.getEmail());
            findOwner.setDateOfBirth(newOwner.getDateOfBirth());
            findOwner.setGender(newOwner.getGender());
            entityManager.getTransaction().commit();
            return newOwner.getFirstName() + " Successfully updated!!!";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public String deleteOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner owner = entityManager.find(Owner.class, ownerId);
            List<Rent_info> rentInfo = owner.getRentInfos();
            if (!rentInfo.isEmpty()) {
                for (Rent_info info : rentInfo) {
                    if (info.getCheckOut().isAfter(LocalDate.now())) {
                        return "cannot delete owner. his house is rented";
                    }
                    Agency agency = info.getAgency();
                    agency.getRentInfos().remove(info);
                    Customer customer = info.getCustomer();
                    customer.getRentInfoList().remove(info);
                    entityManager.remove(info);
                }
            }
            List<Agency> findAgency = owner.getAgencies();
            for (Agency agency : findAgency) {
                agency.getOwners().remove(owner);
            }
            entityManager.remove(owner);
            entityManager.getTransaction().commit();
            return owner.getFirstName() + " Successfully deleted";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public String assignOwnerToAgency(Long ownerId, Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner findOwner = entityManager.find(Owner.class, ownerId);
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            findOwner.getAgencies().add(findAgency);
            findAgency.getOwners().add(findOwner);
            entityManager.getTransaction().commit();
            return "Successfully assigned!!!";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public List<Owner> getOwnersByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o join o.agencies a" +
                            " where a.id =:agencyId", Owner.class)
                    .setParameter("agencyId", agencyId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return owners;
    }

    @Override
    public Map<String, Integer> getOwnerNameAndAge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<String, Integer> namesAndAges = new HashMap<>();
        try {
            entityManager.getTransaction().begin();
            List<Owner> allOwners = entityManager.createQuery("select o from Owner o", Owner.class)
                    .getResultList();
            for (Owner allOwner : allOwners) {
                namesAndAges.put(allOwner.getFirstName(), LocalDate.now().getYear() - allOwner.getDateOfBirth().getYear());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return namesAndAges;
    }

    private void rollback(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }
}