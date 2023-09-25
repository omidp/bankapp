package com.bankapp.bank.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class PO extends AbstractEntity {

    @Basic
    @Column(name="created_date")
    @CreatedDate
    private java.time.LocalDateTime createdDate;

    @Basic
    @Column(name="updated_date")
    @LastModifiedDate
    private java.time.LocalDateTime updatedDate;



}
