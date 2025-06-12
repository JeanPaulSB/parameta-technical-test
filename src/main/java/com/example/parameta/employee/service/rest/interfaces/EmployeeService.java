package com.example.parameta.employee.service.rest.interfaces;

import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface EmployeeService {
    public boolean isAdult(Date birthDate);
    public DateDetailDTO computeAge(Date birthDate);
    void saveEmployee(Employee employee);
}
