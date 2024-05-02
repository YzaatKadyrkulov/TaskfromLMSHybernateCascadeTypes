package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.HouseDao;
import hybernate.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HouseDaoImpl implements HouseDao {
    private final EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public String saveHouse(Long ownerId, House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner owner = entityManager.find(Owner.class, ownerId);
            owner.getHouses().add(house);
            house.setOwner(owner);
            entityManager.persist(house);
            entityManager.getTransaction().commit();

            return house.getHouseType() + " Successfully saved!!!";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public Optional<House> findHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House house = null;
        try {
            entityManager.getTransaction().begin();
            house = entityManager.createQuery("select h from House h where id =:houseId", House.class)
                    .setParameter("houseId", houseId)
                    .getSingleResult();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(house);
    }

    @Override
    public List<House> findAllHouses() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h", House.class)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return houses;
    }

    @Override
    public String updateHouseById(Long houseId, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            House house = entityManager.find(House.class, houseId);

            house.setHouseType(newHouse.getHouseType());
            house.setPrice(newHouse.getPrice());
            house.setRating(newHouse.getRating());
            house.setDescription(newHouse.getDescription());
            house.setRoom(newHouse.getRoom());
            house.setFurniture(newHouse.isFurniture());

            entityManager.getTransaction().commit();

            return newHouse.getHouseType() + " Successfully updated!!!";
        } catch (Exception e) {
            rollback(entityManager);
            return e.getMessage();
        }
    }

    @Override
    public String deleteHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            House finsHouse = entityManager.find(House.class, houseId);
            Rent_info rentInfo = finsHouse.getRentInfo();
            if (rentInfo.getCheckIn() != null) {
                if (rentInfo.getCheckOut().isAfter(LocalDate.now())) {
                    return "cannot be deleted . A client lives in the house";
                }
                Owner owner = rentInfo.getOwner();
                owner.getRentInfos().remove(rentInfo);
                Agency agencyForDelete = rentInfo.getAgency();
                agencyForDelete.getRentInfos().remove(rentInfo);
                Customer infoCustomer = rentInfo.getCustomer();
                infoCustomer.getRentInfoList().remove(rentInfo);
            }
            Address address = finsHouse.getAddress();
            Agency agency = address.getAgency();
            agency.setAddress(null);
            Owner houseOwner = finsHouse.getOwner();
            houseOwner.getHouses().remove(finsHouse);
            entityManager.remove(finsHouse);
            entityManager.getTransaction().commit();
            return finsHouse.getHouseType() + " Successfully deleted";
        } catch (Exception e) {
           rollback(entityManager);
            return  e.getMessage();
        }
    }

    @Override
    public List<House> getHousesInRegion(String region) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.address.region =:region", House.class)
                    .setParameter("region", region).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return houses;
    }

    @Override
    public List<House> allHousesByAgencyId(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.address.agency.id =:agencyId", House.class)
                    .setParameter("agencyId", agencyId).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return houses;
    }



    @Override
    public List<House> allHousesByOwnerId(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            " where h.owner.id =:ownerId", House.class)
                    .setParameter("ownerId", ownerId).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return houses;
    }


    @Override
    public List<House> housesBetweenDates(LocalDate checkIn, LocalDate checkOut) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h " +
                            "where h.rentInfo.checkIn between :from and :to", House.class)
                    .setParameter("from", checkIn)
                    .setParameter("to", checkOut)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return houses;
    }

    private void rollback(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }
}
