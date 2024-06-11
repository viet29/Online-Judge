package com.service.testcase_service.service;

import com.service.testcase_service.model.TestCase;
import com.service.testcase_service.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService{

    private final TestCaseRepository testCaseRepository;

    @Autowired
    public TestCaseServiceImpl(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    public List<TestCase> getAllTestCasesByExerciseId(Long exerciseId) {
        return testCaseRepository.findAllByExerciseId(exerciseId);
    }
}
