package com.bankapp.bank.dao;

import com.bankapp.bank.domain.AddressEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface AddressDao extends ListCrudRepository<AddressEntity, Long> {

}
