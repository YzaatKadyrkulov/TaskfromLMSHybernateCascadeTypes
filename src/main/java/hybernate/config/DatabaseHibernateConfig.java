package hybernate.config;

import hybernate.entity.*;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class DatabaseHibernateConfig {
    public static Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        properties.setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/topics");
        properties.setProperty(Environment.USER, "postgres");
        properties.setProperty(Environment.PASS, "1234");
        properties.setProperty(Environment.HBM2DDL_AUTO, "update");
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty(Environment.FORMAT_SQL, "true");

        return properties;
    }

    public static EntityManagerFactory entityManagerFactory() {

        Configuration configuration = new Configuration();
        configuration.addProperties(properties());
        configuration.addAnnotatedClass(Agency.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Owner.class);
        configuration.addAnnotatedClass(House.class);
        configuration.addAnnotatedClass(Rent_info.class);

        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);
    }
}
