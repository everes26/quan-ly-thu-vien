package com.service.auth.entitys;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @CreatedBy
    private String creator;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @LastModifiedBy
    private String updater;
}
