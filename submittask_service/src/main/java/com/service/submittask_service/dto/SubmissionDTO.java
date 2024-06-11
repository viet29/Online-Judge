package com.service.submittask_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {
    private Long id;
    private String output;
    private Double actualTime;
    private Integer actualMemory;
    private String language;
    private String status;
    private String content;
    private Long exerciseId;

    public SubmissionDTO(String output, Double actualTime, Integer actualMemory, String language,
                         String status, String content, Long exerciseId) {
        this.output = output;
        this.actualTime = actualTime;
        this.actualMemory = actualMemory;
        this.language = language;
        this.status = status;
        this.content = content;
        this.exerciseId = exerciseId;
    }
}
