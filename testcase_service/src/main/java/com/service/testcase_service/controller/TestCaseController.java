package com.service.testcase_service.controller;

import com.service.testcase_service.model.TestCase;
import com.service.testcase_service.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping
    public ResponseEntity<List<TestCase>> getAllTestCasesByExerciseId(@RequestParam(name = "exerciseId") Long exerciseId) {
        List<TestCase> testCaseList = testCaseService.getAllTestCasesByExerciseId(exerciseId);
        return ResponseEntity.ok(testCaseList);
    }
}
