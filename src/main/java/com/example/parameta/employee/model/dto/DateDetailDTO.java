package com.example.parameta.employee.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateDetailDTO {
    private int years;
    private int months;
    private int days;
}
