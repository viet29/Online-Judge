package com.service.submittask_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeExecutionRequest {
    private String lang;
    private String source;
    private List<Test> tests;
    private Boolean all_fatal;
    private Execute execute;

    public CodeExecutionRequest(String lang, String source, List<Test> tests) {
        this.lang = lang;
        this.source = source;
        this.tests = tests;
        all_fatal = true;
    }
}
