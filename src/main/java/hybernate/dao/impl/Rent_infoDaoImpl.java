package hybernate.dao.impl;

import hybernate.config.DatabaseHibernateConfig;
import hybernate.dao.Rent_infoDao;
import hybernate.entity.Rent_info;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rent_infoDaoImpl implements Rent_infoDao {
    private final EntityManagerFactory entityManagerFactory = DatabaseHibernateConfig.entityManagerFactory();

    @Override
    public List<Rent_info> rentInfoBetweenDates(LocalDate checkIn, LocalDate checkOut) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Rent_info> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select r from Rent_info r " +
                                    " where r.checkOut between :from and :to",
                            Rent_info.class)
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


    @Override
    public Long housesByAgencyIdAndDate(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long countHouse = 0L;
        try {
            entityManager.getTransaction().begin();

            countHouse = entityManager.createQuery("""
            select count(r) from Rent_info r
            where r.agency.id =:agencyId and r.checkIn <=:currentDate
            and r.checkOut >=:currentDate
            """, Long.class)
                    .setParameter("agencyId", agencyId)
                    .setParameter("currentDate", LocalDate.now())
                    .getSingleResult();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            rollback(entityManager);
            System.out.println(e.getMessage());
        }
        return countHouse;
    }

    private void rollback(EntityManager entityManager) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            entityManager.close();
        }
    }
}
