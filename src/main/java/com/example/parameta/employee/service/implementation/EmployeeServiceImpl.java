package com.example.parameta.employee.service.implementation;
import com.example.employee.Employee;
import com.example.employee.StoreEmployeeRequest;
import com.example.parameta.employee.client.SoapClient;
import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.dto.EmployeeRequestDTO;
import com.example.parameta.employee.model.dto.EmployeeResponseDTO;
import com.example.parameta.employee.repository.EmployeeRepository;
import com.example.parameta.employee.service.interfaces.EmployeeService;
import com.example.parameta.exception.custom.DuplicatedException;
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


    /**
     * Processes employee information, validates age, sends to SOAP service, and returns response DTO
     * @param employeeRequestDTO the employee information to process
     * @return EmployeeResponseDTO containing processed employee information
     * @throws UnderageException if employee is under 18 years old
     */
    public EmployeeResponseDTO processEmployee(EmployeeRequestDTO employeeRequestDTO){
        if (!this.isAdult(employeeRequestDTO.getBirthDate())) {
            throw new UnderageException("Employee must have at least 18 years old.");
        }
        if(employeeRepository.findByDocumentNumber(employeeRequestDTO.getDocumentNumber()).isPresent()){
            throw new DuplicatedException("Document number already exists.");
        };
        // builds Employee request for SOAP service
        Employee employee = new Employee();
        employee.setName(employeeRequestDTO.getName());
        employee.setDocumentNumber(employeeRequestDTO.getDocumentNumber());
        employee.setDocumentType(employeeRequestDTO.getDocumentType());
        employee.setHireDate(DateUtil.toXMLGregorianCalendar(employeeRequestDTO.getHireDate()));
        employee.setLastName(employeeRequestDTO.getLastName());
        employee.setPosition(employeeRequestDTO.getPosition());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setBirthDate(DateUtil.toXMLGregorianCalendar(employeeRequestDTO.getBirthDate()));

        // calls SOAP service to store employee
        StoreEmployeeRequest storeEmployeeRequest = new StoreEmployeeRequest();
        storeEmployeeRequest.setEmployee(employee);
        soapClient.callSoapService("http://localhost:8080/ws", storeEmployeeRequest);

        // builds and returns EmployeeResponseDTO
        return this.buildEmployeeResponseDTO(employeeRequestDTO);

    }

    /**
     * Builds response DTO from request DTO, including age and time in company calculations
     * @param employee the employee request DTO
     * @return EmployeeResponseDTO with complete employee information
     */
    public EmployeeResponseDTO buildEmployeeResponseDTO(EmployeeRequestDTO employee) {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setName(employee.getName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setDocumentType(employee.getDocumentType());
        employeeResponseDTO.setDocumentNumber(employee.getDocumentNumber());
        employeeResponseDTO.setBirthDate(employee.getBirthDate());
        employeeResponseDTO.setHireDate(employee.getHireDate());
        employeeResponseDTO.setPosition(employee.getPosition());
        employeeResponseDTO.setSalary(employee.getSalary());
        employeeResponseDTO.setAge(computeAge(employee.getBirthDate()));
        employeeResponseDTO.setTimeInCompany(computeTimeInCompany(employee.getHireDate()));
        return employeeResponseDTO;
    }

    /**
     * Saves employee information from SOAP request to database
     * @param request the SOAP request containing employee data
     */
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

    /**
     * Calculates age from birth date
     * @param birthDate the date of birth
     * @return DateDetailDTO containing years, months, and days
     */
    @Override
    public DateDetailDTO computeAge(Date birthDate) {
        LocalDate localDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(localDate, today);
        return new DateDetailDTO(period.getYears(), period.getMonths(), period.getDays());
    }

    /**
     * Calculates time worked in company from hire date
     * @param hireDate the date of hire
     * @return DateDetailDTO containing years, months, and days
     */
    @Override
    public DateDetailDTO computeTimeInCompany(Date hireDate) {
        LocalDate localDate = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(localDate, today);
        return new DateDetailDTO(period.getYears(), period.getMonths(), period.getDays());
    }

    /**
     * Checks if person is at least 18 years old
     * @param birthDate the date of birth to check
     * @return true if 18 or older, false otherwise
     */
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
