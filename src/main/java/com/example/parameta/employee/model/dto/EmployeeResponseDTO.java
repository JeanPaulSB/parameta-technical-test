package com.example.parameta.employee.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EmployeeResponseDTO {
    private String name;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private Date birthDate;
    private Date hireDate;
    private String position;
    private Double salary;
    private DateDetailDTO age;
    private DateDetailDTO timeInCompany;
}
