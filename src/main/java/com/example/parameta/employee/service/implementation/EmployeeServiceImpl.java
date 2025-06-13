package com.example.parameta.employee.service.implementation;
import com.example.employee.Employee;
import com.example.employee.StoreEmployeeRequest;
import com.example.parameta.employee.client.SoapClient;
import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.dto.EmployeeRequestDTO;
import com.example.parameta.employee.model.dto.EmployeeResponseDTO;
import com.example.parameta.employee.repository.EmployeeRepository;
import com.example.parameta.employee.service.interfaces.EmployeeService;
import com.example.parameta.exception.custom.UnderageException;
import com.example.parameta.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SoapClient soapClient;



    public EmployeeResponseDTO processEmployee(EmployeeRequestDTO employeeRequestDTO){
        if (!this.isAdult(employeeRequestDTO.getBirthDate())) {
            throw new UnderageException("Employee must have at least 18 years old.");
        }
        // building Employee
        Employee employee = new Employee();
        employee.setName(employeeRequestDTO.getName());
        employee.setDocumentNumber(employeeRequestDTO.getDocumentNumber());
        employee.setDocumentType(employeeRequestDTO.getDocumentType());
        employee.setHireDate(DateUtil.toXMLGregorianCalendar(employeeRequestDTO.getHireDate()));
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setPosition(employeeRequestDTO.getPosition());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setBirthDate(DateUtil.toXMLGregorianCalendar(employeeRequestDTO.getBirthDate()));

        StoreEmployeeRequest storeEmployeeRequest = new StoreEmployeeRequest();
        storeEmployeeRequest.setEmployee(employee);

        soapClient.callSoapService("http://localhost:8080/ws", storeEmployeeRequest);

        DateDetailDTO ageDetailDTO = this.computeAge(employeeRequestDTO.getBirthDate());
        DateDetailDTO timeInCompany = this.computeTimeInCompany(employeeRequestDTO.getHireDate());

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setName(employeeRequestDTO.getName());
        employeeResponseDTO.setDocumentNumber(employeeRequestDTO.getDocumentNumber());
        employeeResponseDTO.setDocumentType(employeeRequestDTO.getDocumentType());
        employeeResponseDTO.setPosition(employeeRequestDTO.getPosition());
        employeeResponseDTO.setSalary(employeeRequestDTO.getSalary());
        employeeResponseDTO.setAge(ageDetailDTO);
        employeeResponseDTO.setTimeInCompany(timeInCompany);
        employeeResponseDTO.setBirthDate(employeeRequestDTO.getBirthDate());
        employeeResponseDTO.setHireDate(employeeRequestDTO.getHireDate());

        return employeeResponseDTO;

    }

    @Override
    public void saveEmployee(StoreEmployeeRequest request){
        Employee  employee = request.getEmployee();
        com.example.parameta.employee.model.entity.Employee employeeEntity = new com.example.parameta.employee.model.entity.Employee();
        employeeEntity.setName(employee.getName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setPosition(employee.getPosition());
        employeeEntity.setSalary(employee.getSalary());
        employeeEntity.setDocumentNumber(employee.getDocumentNumber());
        employeeEntity.setDocumentType(employee.getDocumentType());
        employeeEntity.setHireDate(DateUtil.fromXMLGregorianCalendar(employee.getHireDate()));
        employeeEntity.setBirthDate(DateUtil.fromXMLGregorianCalendar(employee.getBirthDate()));
        employeeRepository.save(employeeEntity);
    }


    @Override
    public DateDetailDTO computeAge(Date birthDate) {
        LocalDate localDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(localDate, today);
        return new DateDetailDTO(period.getYears(), period.getMonths(), period.getDays());
    }

    @Override
    public DateDetailDTO computeTimeInCompany(Date hireDate) {
        LocalDate localDate = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(localDate, today);
        return new DateDetailDTO(period.getYears(), period.getMonths(), period.getDays());
    }

    @Override
    public boolean isAdult(Date birthDate) {
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);
        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) == birth.get(Calendar.DAY_OF_YEAR)) {
            age = age - 1;
        }
        return age >= 18;

    }
}
