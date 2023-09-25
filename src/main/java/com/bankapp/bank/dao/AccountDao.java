package com.bankapp.bank.dao;

import com.bankapp.bank.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountDao extends CrudRepository<AccountEntity, Long>, ListPagingAndSortingRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {

    @Query("from AccountEntity ac where ac.iban like :accountNo")
    Optional<AccountEntity> findByAccountNo(@Param("accountNo") String accountNo);
}
