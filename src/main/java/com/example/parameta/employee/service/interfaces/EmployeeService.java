package com.example.parameta.employee.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface EmployeeService {
    public boolean isAdult(Date birthDate);
}
