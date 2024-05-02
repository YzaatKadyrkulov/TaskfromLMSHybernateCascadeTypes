package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.AgencyDao;
import hybernate.entity.Address;
import hybernate.entity.Agency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgencyDaoImpl implements AgencyDao {
    EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public String saveAgency(Agency agency,Address address) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(agency);
            entityManager.persist(address);

            agency.setAddress(address);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
        return "Agency and Address successfully saved";
    }

    @Override
    public Optional<Agency> findAgencyById(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agency agency = null;
        try {
            entityManager.getTransaction().begin();
            agency = entityManager.find(Agency.class, agencyId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);

        }
        return Optional.ofNullable(agency);
    }

    @Override
    public List<Agency> findAllAgencies() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Agency> agencies = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            agencies = entityManager.createQuery("select a from Agency a", Agency.class)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            System.err.println(e.getMessage());
        }
        return agencies;
    }

    @Override
    public String updateAgencyById(Long agencyId, Agency newAgency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agency agencyToUpdate = entityManager.find(Agency.class, agencyId);

        try {
            entityManager.getTransaction().begin();
            if (agencyToUpdate != null) {
                agencyToUpdate.setName(newAgency.getName());
                agencyToUpdate.setPhoneNumber(newAgency.getPhoneNumber());
                entityManager.getTransaction().commit();
                return "Agency with ID " + agencyId + " updated successfully";
            } else {
                return "Agency with ID " + agencyId + " not found";
            }
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public String deleteAgencyById(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, agencyId);

            entityManager.remove(agency);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollBack(entityManager);
            return e.getMessage();
        }
        return "Successfully removed";
    }

    @Override
    public void assignAddressIdToAgency(Long addressId, Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Address address = entityManager.find(Address.class, addressId);

            Agency agency = entityManager.createQuery("select a from Agency a where a.id = : agencyId", Agency.class).
                    setParameter("agencyId", agencyId)
                    .getSingleResult();
            agency.setAddress(address);

            System.out.println("Successfully saved:");
        } catch (Exception e) {
            rollBack(entityManager);
            System.out.println(e.getMessage());
        }
    }

    public void rollBack(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }
}
