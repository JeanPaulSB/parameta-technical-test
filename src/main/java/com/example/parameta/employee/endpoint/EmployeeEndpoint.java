package com.example.parameta.employee.endpoint;

import com.example.employee.StoreEmployeeRequest;
import com.example.employee.StoreEmployeeResponse;
import com.example.parameta.employee.model.entity.Employee;
import com.example.parameta.employee.repository.EmployeeRepository;
import com.example.parameta.employee.service.interfaces.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@AllArgsConstructor
public class EmployeeEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/employee";
    private EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "storeEmployeeRequest")
    @ResponsePayload
    public StoreEmployeeResponse getEmployee(@RequestPayload StoreEmployeeRequest employeeRequest) {
        StoreEmployeeResponse response = new StoreEmployeeResponse();
        employeeService.saveEmployee(employeeRequest);
        return response;
    }
}