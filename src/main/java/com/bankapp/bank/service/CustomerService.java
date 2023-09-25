package com.bankapp.bank.service;

import com.bankapp.bank.dao.AccountDao;
import com.bankapp.bank.domain.AccountEntity;
import com.bankapp.bank.domain.CustomerEntity;
import com.bankapp.bank.model.AccountInfoResponse;
import com.bankapp.bank.model.EntityMapper;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AccountDao accountDao;

    public List<AccountInfoResponse> getCustomerAccountInfo(Long userId) {
        return accountDao.findAll((Specification<AccountEntity>) (root, query, criteriaBuilder) -> {
                    Subquery<CustomerEntity> customerSubQuery = query.subquery(CustomerEntity.class);
                    Root<CustomerEntity> fromCustomer = customerSubQuery.from(CustomerEntity.class);
                    customerSubQuery.select(fromCustomer);
                    customerSubQuery.where(criteriaBuilder.equal(fromCustomer.get("user").get("id"), userId));

                    return criteriaBuilder.equal(root.get("customer"), customerSubQuery);
                }, Pageable.ofSize(50))
                .getContent()
                .stream().map(EntityMapper::toAccountInfoResponse).collect(Collectors.toList());
    }

}
