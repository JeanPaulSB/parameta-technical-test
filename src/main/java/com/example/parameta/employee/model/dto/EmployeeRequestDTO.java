package com.example.parameta.employee.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class EmployeeRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Document type cannot be empty")
    private String documentType;

    @NotBlank(message = "Document number cannot be empty")
    private String documentNumber;

    @NotBlank(message = "Birth date cannot be empty")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @NotBlank(message = "Hire date cannot be empty")
    private Date hireDate;


    @NotBlank(message = "Position cannot be empty")
    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    @Positive
    private Double salary;
}
