package com.service.submission_service.service;

import com.service.submission_service.model.Submission;

import java.util.List;

public interface SubmissionService {
    List<Submission> getAllSubmissions();
    Submission addSubmission(Submission submission);
}
