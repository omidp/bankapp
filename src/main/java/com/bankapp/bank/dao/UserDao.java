package com.bankapp.bank.dao;

import com.bankapp.bank.domain.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends ListCrudRepository<UserEntity, Long> {

    @Query("from UserEntity u where u.username = :uname")
    Optional<UserEntity> findByUsername(@Param("uname") String username);

}
