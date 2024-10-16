package com.task.library_management_system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONTACTS")
public class Contact extends BaseEntity {

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

}
