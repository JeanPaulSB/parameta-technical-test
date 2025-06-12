package com.example.parameta.employee.controller;

import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.dto.EmployeeRequestDTO;
import com.example.parameta.employee.service.rest.interfaces.EmployeeService;
import com.example.parameta.exception.custom.UnderageException;
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
        if (!employeeService.isAdult(employeeRequestDTO.getBirthDate())) {
            throw new UnderageException("Employee must have at least 18 years old.");
        }

        DateDetailDTO dateDetailDTO = employeeService.computeAge(employeeRequestDTO.getBirthDate());

        return ResponseEntity.status(HttpStatus.OK).body(dateDetailDTO.getDays());
    }

}
