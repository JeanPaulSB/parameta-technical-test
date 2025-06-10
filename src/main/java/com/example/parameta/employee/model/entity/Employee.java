package com.example.parameta.employee.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "document_type")
    private String documentType;

    @Column(nullable = false, name = "document_number", unique = true)
    private String documentNumber;

    @Column(nullable = false, name = "birth_date")
    private Date birthDate;

    @Column(nullable = false, name = "hire_date")
    private Date hireDate;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Double salary;
}
