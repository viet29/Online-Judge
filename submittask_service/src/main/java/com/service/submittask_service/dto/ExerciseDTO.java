package com.service.submittask_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private Long id;
    private String code;
    private String name;
    private String detail;
    private Double timeLimit;   // second
    private Integer memoryLimit; // kB
}
