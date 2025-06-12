package com.example.parameta.employee.service.rest.implementation;

import com.example.parameta.employee.model.dto.DateDetailDTO;
import com.example.parameta.employee.model.entity.Employee;
import com.example.parameta.employee.repository.EmployeeRepository;
import com.example.parameta.employee.service.rest.interfaces.EmployeeService;
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

    @Override
    public  void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }


    @Override
    public DateDetailDTO computeAge(Date birthDate) {
        LocalDate localDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
