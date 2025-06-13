package com.example.parameta.employee.service.interfaces;

import com.example.employee.StoreEmployeeRequest;
import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.dto.EmployeeRequestDTO;
import com.example.parameta.employee.model.dto.EmployeeResponseDTO;
import com.example.parameta.employee.model.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface EmployeeService {
    public boolean isAdult(Date birthDate);
    public DateDetailDTO computeAge(Date birthDate);
    public DateDetailDTO computeTimeInCompany(Date hireDate);
    public void saveEmployee(StoreEmployeeRequest request);
    public EmployeeResponseDTO processEmployee(EmployeeRequestDTO employeeRequestDTO);
}
