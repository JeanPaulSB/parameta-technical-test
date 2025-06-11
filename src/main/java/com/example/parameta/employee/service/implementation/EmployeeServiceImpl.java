package com.example.parameta.employee.service.implementation;

import com.example.parameta.employee.service.interfaces.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Calendar;
import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public boolean isAdult(Date birthDate) {
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthDate);

        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if(today.get(Calendar.DAY_OF_YEAR) == birth.get(Calendar.DAY_OF_YEAR)) {
            age = age - 1;
        }
        return age >=18;

    }
}
