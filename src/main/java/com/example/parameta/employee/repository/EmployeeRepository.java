package com.example.parameta.employee.repository;

import com.example.parameta.employee.model.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    Optional<Employee> findByDocumentNumber(String documentNumber);

}
