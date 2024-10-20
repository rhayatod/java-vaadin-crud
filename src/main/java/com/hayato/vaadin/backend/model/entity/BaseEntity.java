package com.hayato.vaadin.backend.model.entity;


import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(name = "create_date", length = 14, nullable = false, updatable=false)
   // @JsonFormat(pattern="yyyyMMddHHmmss")
    @CreationTimestamp
    private Timestamp createDate;

    @Column(name = "create_user", length = 50, nullable = false)
    private String createUser;

    @Column(name = "update_date", length = 14, nullable = false, updatable=false)
    @UpdateTimestamp
    private Timestamp updateDate;

    @Column(name = "update_user", length = 50, nullable = false)
    private String updateUser;
}
