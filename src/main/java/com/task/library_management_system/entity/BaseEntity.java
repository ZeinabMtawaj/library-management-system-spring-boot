package com.task.library_management_system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.descriptor.jdbc.UUIDJdbcType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "_UUID", unique = true, nullable = false, updatable = false)
    @JdbcType(UUIDJdbcType.class)
    @UuidGenerator
    private UUID uuid;

    @Version
    @Column(name = "_VERSION")
    private int version;

    @CreatedDate
    @Column(name = "CREATION_DATE")
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFICATION_DATE")
    private Instant lastModificationDate;


    @Column(name = "DELETED")
    private boolean deleted;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isNew() {
        return this.id == null;
    }

}
