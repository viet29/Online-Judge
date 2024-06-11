package com.service.testcase_service.service;

import com.service.testcase_service.model.TestCase;

import java.util.List;

public interface TestCaseService {
    List<TestCase> getAllTestCasesByExerciseId(Long exerciseId);
}
