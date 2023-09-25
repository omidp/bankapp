package com.bankapp.bank.service;

import com.bankapp.bank.domain.AccountEntity;
import com.bankapp.bank.domain.CustomerEntity;
import com.bankapp.bank.domain.UserEntity;
import com.bankapp.bank.infra.AbstractIntegrationTest;
import com.bankapp.bank.infra.TestConfigContext;
import com.bankapp.bank.model.AccountIbanInfo;
import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.model.EntityMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import(TestConfigContext.class)
public class IbanServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private IbanService ibanService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager ptm;

    private AccountEntity account;

    @BeforeEach
    void setup() {
        new TransactionTemplate(ptm).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                UserEntity userEntity = new UserEntity("test", "test", true);
                entityManager.persist(userEntity);
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
    void testUniqueIban() {
        AccountIbanInfo info = ibanService.getIban(new DummyAccountGenerator());
        assertEquals("NL91ABNA12345678", info.iban());
        assertEquals("12345678", info.accountNo());
    }


    private class DummyAccountGenerator implements AccountNoGenerator {
        private int cnt = 0;

        @Override
        public String generate() {
            if (cnt == 0) {
                cnt++;
                return account.getIban();
            }
            return "12345678";
        }
    }

}
