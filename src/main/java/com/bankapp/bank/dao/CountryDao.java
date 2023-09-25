package com.bankapp.bank.dao;

import com.bankapp.bank.domain.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountryDao extends JpaRepository<CountryEntity, Long> {

    @Query("from CountryEntity c where c.name = :name")
    Optional<CountryEntity> findByName(@Param("name") String name);

}
