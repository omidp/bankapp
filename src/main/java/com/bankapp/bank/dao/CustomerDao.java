package com.bankapp.bank.dao;

import com.bankapp.bank.domain.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface CustomerDao extends ListCrudRepository<CustomerEntity, Long> {
}
