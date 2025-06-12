package com.example.parameta.employee.repository;

import com.example.parameta.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
}
