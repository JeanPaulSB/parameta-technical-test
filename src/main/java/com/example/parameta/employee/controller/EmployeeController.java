package com.example.parameta.employee.controller;

import com.example.employee.StoreEmployeeRequest;
import com.example.employee.Employee;
import com.example.parameta.employee.client.SoapClient;
import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.dto.EmployeeRequestDTO;
import com.example.parameta.employee.model.dto.EmployeeResponseDTO;
import com.example.parameta.employee.service.interfaces.EmployeeService;
import com.example.parameta.exception.custom.UnderageException;
import com.example.parameta.util.DateUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployeeInfo(@Valid @ModelAttribute EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.processEmployee(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDTO);
    }

}
