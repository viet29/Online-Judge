package com.service.submittask_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    private String name;
    private String stdin;
    private Integer mem;    // kB
    private Double time;   // second
}
