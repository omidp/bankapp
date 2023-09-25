package com.bankapp.bank.service;

import com.bankapp.bank.config.JpaConfig;
import com.bankapp.bank.dao.AccountDao;
import com.bankapp.bank.domain.AccountEntity;
import com.bankapp.bank.domain.CustomerEntity;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.infra.AbstractIntegrationTest;
import com.bankapp.bank.infra.PostgreSQLProperties;
import com.bankapp.bank.infra.PostgresContainerHolder;
import com.bankapp.bank.infra.TestConfigContext;
import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.model.EntityMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(TestConfigContext.class)
public class CustomerServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager ptm;

    private AccountEntity account;
    private Long userId;

    @BeforeEach
    void setup() {
        new TransactionTemplate(ptm).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                UserEntity userEntity = new UserEntity("test", "test", true);
                entityManager.persist(userEntity);
                userId = userEntity.getId();
                CustomerEntity customer = new CustomerEntity();
                customer.setDob("1986-05-21");
                customer.setName("customer1");
                customer.setUser(userEntity);
                entityManager.persist(customer);
                account = new AccountEntity();
                account.setCustomer(customer);
                account.setAccountNo("3423423");
                account.setIban("NL91ABNA3423423");
                account.setAccountType(AccountEntity.AccountType.CREDIT);
                account.setBalance(BigDecimal.TEN);
                entityManager.persist(account);
            }
        });

    }

    @AfterEach
    void teardown() {
        new TransactionTemplate(ptm).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                entityManager.createQuery("delete from AccountEntity").executeUpdate();
                entityManager.createQuery("delete from CustomerEntity").executeUpdate();
                entityManager.createQuery("delete from UserEntity").executeUpdate();
            }
        });
    }

    @Test
    void testAccountInfo() {
        List<AccountInfoResponse> actual = customerService.getCustomerAccountInfo(userId);
        assertTrue(actual.contains(EntityMapper.toAccountInfoResponse(account)));
    }


}
