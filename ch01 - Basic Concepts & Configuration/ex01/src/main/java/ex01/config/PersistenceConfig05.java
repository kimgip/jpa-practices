package ex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"ex01.repository"})
public class PersistenceConfig05 {

    // 1. DataSource
    @Primary
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://192.168.66.4:3306/jpadb?charset=utf8");
        dataSource.setUsername("jpadb");
        dataSource.setPassword("jpadb");

        return dataSource;
    }

    // 2. EntityManagerFactory(Proxy to LocalContainerEntityManagerFactoryBean)
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Throwable {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        // Persistence Unit Name
        emf.setPersistenceUnitName("jpadb");

        // Scanning Entity at Base Packages
        emf.setPackagesToScan("ex01.domain01_05");

        // Entity Mapping XMLs
        // emf.setMappingResources("");

        // DataSource
        emf.setDataSource(dataSource());

        // Vendor Adapter (Hibernate)
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(jpaVendorAdapter);

        // Vendor Adapter Configuration: Vendors' Common Configuration
        // jpaVendorAdapter.setDatabase(Database.MYSQL);
        // jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MariaDB106Dialect");
        // jpaVendorAdapter.setGenerateDdl(true);
        // jpaVendorAdapter.setShowSql(true);

        // Vendor Provider Configuration: a Specific Vendor's Internal Configuration
        emf.setJpaProperties(hibernateProperties());

        return emf;
    }


    // 3. PlatformTransactionManager(JpaTransactionManager)
    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() throws Throwable {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    Properties hibernateProperties() {
        Properties properties = new Properties();

        /* a Specific Vendor Provider(Hibenate) Internal Configurations */
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDB106Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");

        return properties;
    }
}