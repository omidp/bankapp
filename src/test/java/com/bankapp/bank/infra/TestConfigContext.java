package com.bankapp.bank.infra;

import com.bankapp.bank.config.CustomerConfiguration;
import com.bankapp.bank.config.JpaConfig;
import com.bankapp.bank.config.LocationConfig;
import com.bankapp.bank.dao.AccountDao;
import com.bankapp.bank.domain.CustomerEntity;
import com.bankapp.bank.service.CustomerService;
import com.bankapp.bank.service.LocationService;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@TestConfiguration
@Import({JpaConfig.class, LocationConfig.class})
@ComponentScan(basePackageClasses = CustomerService.class)
@EnableConfigurationProperties({CustomerConfiguration.class})
public class TestConfigContext {

    @Bean("postgresContainerHolder")
    PostgresContainerHolder postgresContainerHolder() {
        return new PostgresContainerHolder(new PostgreSQLProperties());
    }

    @Bean("postgresDataSource")
    @DependsOn("postgresContainerHolder")
    DataSource postgresDataSource(PostgresContainerHolder containerHolder) {
        return new DriverManagerDataSource(
                containerHolder.getContainer().getJdbcUrl(),
                containerHolder.getContainer().getUsername(),
                containerHolder.getContainer().getPassword()
        );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaProperties jpaProps) {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        jpaVendorAdapter.setGenerateDdl(true);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaPropertyMap(jpaProps.getProperties());
        factoryBean.setPackagesToScan(CustomerEntity.class.getPackage().getName());
        factoryBean.setPersistenceUnitName("persistenceUnit");
        return factoryBean;
    }

    @Bean("jpaProps")
    public JpaProperties jpaProperties() {
        JpaProperties prop = new JpaProperties();
        prop.setShowSql(true);
        prop.setDatabase(Database.POSTGRESQL);
        prop.setGenerateDdl(true);
        Map<String, String> m = new HashMap<>();
        m.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        m.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        m.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        prop.setProperties(m);
        return prop;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        var propHolder = new PropertySourcesPlaceholderConfigurer();
        var prop = new Properties();
        prop.put("customer.country-validation","ADDRESS");
        prop.put("customer.countries","Netherlands,Germany");
        propHolder.setProperties(prop);
        return propHolder;
    }

}