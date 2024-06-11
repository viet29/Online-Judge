package com.service.submittask_service.controller;

import com.service.submittask_service.dto.MessageDTO;
import com.service.submittask_service.dto.SubmissionDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import com.service.submittask_service.dto.ExerciseDTO;
import com.service.submittask_service.dto.TestCaseDTO;
import com.service.submittask_service.helper.Util;
import com.service.submittask_service.model.CodeExecutionRequest;
import com.service.submittask_service.model.Execute;
import com.service.submittask_service.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    private final RestTemplate restTemplate;
    private final Util util;

    private static final String GET_EXERCISE_BY_CODE_URL = "http://localhost:8081/exercises";
    private static final String GET_ALL_TESTCASES_BY_EXERCISE_ID_URL = "http://localhost:8085/testcases?exerciseId=";
    private static final String EXECUTION_URL = "http://192.168.129.130:42920/run";
    private static final String SAVE_SUBMISSION_URL = "http://localhost:8083/submissions";
    private static final String SEND_MESSAGE_URL = "http://localhost:8082/socket/send";


    @Autowired
    public SubmitController(RestTemplate restTemplate,
                            Util util) {
        this.restTemplate = restTemplate;
        this.util = util;
    }

    @PostMapping(value = "/{exerciseCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submit(@RequestParam("file") MultipartFile file,
                                    @RequestParam("language") String languageId,
                                    @PathVariable("exerciseCode") String exerciseCode) throws InterruptedException {
        String language = util.getLanguage(Integer.valueOf(languageId));
        ExerciseDTO exerciseDTO = restTemplate
                .getForObject(GET_EXERCISE_BY_CODE_URL + exerciseCode,
                        ExerciseDTO.class);



        // Bước 1: Xác nhận file hợp lệ
        if (!util.hasValidExtension(file, language)) {
            Thread.sleep(1000);
            // Thong bao that bai va tra ve response
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thất bại", 1, 0,
                            "File không hợp lệ!"), MessageDTO.class);
            return new ResponseEntity<>("File không hợp lệ!", HttpStatus.BAD_REQUEST);
        } else {
            Thread.sleep(1000);
            // Thông báo thành công và tiếp tục
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thành công", 1, 1,
                            "File nộp thành công!"), MessageDTO.class);
        }

        // Bước 2: Thực thi file lấy output thực tế
        String fileContent;
        try {
            fileContent = util.multipartFileReader(file);
            System.out.println(fileContent);
        } catch (IOException e) {
            Thread.sleep(1000);
            //Thông báo thất bại
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thất bại", 2, 0,
                            "Không thể đọc được dữ liệu!"), MessageDTO.class);
            return new ResponseEntity<>("Không đọc được file!", HttpStatus.BAD_REQUEST);
        }
        ParameterizedTypeReference<List<TestCaseDTO>> responseType =
                new ParameterizedTypeReference<List<TestCaseDTO>>() {};
        ResponseEntity<List<TestCaseDTO>> responseTC = restTemplate
                .exchange(GET_ALL_TESTCASES_BY_EXERCISE_ID_URL + exerciseDTO.getId(),
                HttpMethod.GET,
                        null,
                        responseType);
        List<TestCaseDTO> testCaseDTOList = responseTC.getBody();
        Integer memoryLimit = exerciseDTO.getMemoryLimit();
        Double timeLimit = exerciseDTO.getTimeLimit();

        List<Test> testList = new ArrayList<>();
        int count = 0;
        for (TestCaseDTO testCase : testCaseDTOList) {
            if(testCase.getInput() != null)
                testList.add(new Test(String.valueOf(++count), testCase.getInput().trim(), memoryLimit, timeLimit));
            else
                testList.add(new Test(String.valueOf(++count), null, memoryLimit, timeLimit));
        }

        CodeExecutionRequest requestBody = new CodeExecutionRequest(language, fileContent, testList);
        Execute executeRequestBody = new Execute(exerciseDTO.getTimeLimit() * testCaseDTOList.size());
        requestBody.setExecute(executeRequestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<CodeExecutionRequest> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(EXECUTION_URL, HttpMethod.POST, entity, String.class);

        JSONObject response = new JSONObject(responseEntity.getBody());
        Thread.sleep(1000);
        // Thông báo thực thi file thành công
        restTemplate.postForObject(SEND_MESSAGE_URL,
                new MessageDTO("Thành công", 2, 1,
                        "Thực thi file và lấy output thành công"), MessageDTO.class);

        SubmissionDTO submission = new SubmissionDTO();
        submission.setExerciseId(exerciseDTO.getId());
        submission.setLanguage(language);
        submission.setContent(fileContent);

        // Nếu có key "tests" (không xuất hiện lỗi biên dịch)
        if (response.has("tests")) {
            JSONArray testsArray = response.getJSONArray("tests");  //Lấy các test
            double avgTime = 0d;
            int avgMem = 0;
            // Duyệt qua từng test
            for (int i = 0; i < testsArray.length(); i++) {
                JSONObject testObject = testsArray.getJSONObject(i);

                JSONObject metaObject = testObject.getJSONObject("meta");
                String status = metaObject.getString("status");
                double runTime = metaObject.getDouble("time");
                int memory = metaObject.getInt("max-rss");

                // Nếu không gặp lỗi thực thi
                if (util.failedStatusConverter(status) == null) {
                    avgTime += runTime;
                    avgMem += memory;

                    // Loại bỏ ký tự trống ở cuối
                    String stdOut = testObject.getString("stdout").trim();
                    while(stdOut.endsWith("\n") || stdOut.endsWith("\t")) {
                        stdOut = stdOut.substring(0, stdOut.length() - 2);
                    }

                    if(!stdOut.equals(testCaseDTOList.get(i).getOutput().trim())) {    // Kiểm tra WA
                        submission.setStatus("WA");
                        submission.setActualMemory(avgMem / (i + 1));
                        submission.setActualTime(avgTime / (double)(i + 1));

                        Thread.sleep(1000);
                        restTemplate.postForObject(SEND_MESSAGE_URL,
                                new MessageDTO("Thành công", 3, 1,
                                        "So sánh thành công, gặp lỗi WA"), MessageDTO.class);

                        SubmissionDTO submissionDTO = restTemplate
                                .postForObject(SAVE_SUBMISSION_URL, submission, SubmissionDTO.class);

                        Thread.sleep(1000);
                        restTemplate.postForObject(SEND_MESSAGE_URL,
                                new MessageDTO("Thành công", 4, 1,
                                        "Lưu kết quả lần nộp thành công!"), MessageDTO.class);
                        return ResponseEntity.ok(submissionDTO);
                    }

                } else {
                    Thread.sleep(1000);
                    restTemplate.postForObject(SEND_MESSAGE_URL,
                            new MessageDTO("Thành công", 3, 1,
                                    "So sánh thành công, gặp lỗi " + util.failedStatusConverter(status)), MessageDTO.class);

                    submission.setStatus(util.failedStatusConverter(status));
                    submission.setActualTime(runTime);
                    submission.setActualMemory(memory);
                    SubmissionDTO submissionDTO = restTemplate
                            .postForObject(SAVE_SUBMISSION_URL, submission, SubmissionDTO.class);

                    Thread.sleep(1000);
                    restTemplate.postForObject(SEND_MESSAGE_URL,
                            new MessageDTO("Thành công", 4, 1,
                                    "Lưu kết quả lần nộp thành công!"), MessageDTO.class);
                    return ResponseEntity.ok(submissionDTO);
                }
            }
            Thread.sleep(1000);
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thành công", 3, 1,
                            "So sánh thành công, kết quả chính xác!"), MessageDTO.class);

            submission.setStatus("AC");
            submission.setActualTime(avgTime / (double) testsArray.length());
            submission.setActualMemory(avgMem / testsArray.length());
            SubmissionDTO submissionDTO = restTemplate
                    .postForObject(SAVE_SUBMISSION_URL, submission, SubmissionDTO.class);
            Thread.sleep(1000);
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thành công", 4, 1,
                            "Lưu kết quả lần nộp thành công!"), MessageDTO.class);
            return ResponseEntity.ok(submissionDTO);
        } else {
            Thread.sleep(1000);
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thành công", 3, 1,
                            "Chạy chương trình thành công, biên dịch thất bại!"), MessageDTO.class);
            submission.setStatus("CE");
            SubmissionDTO submissionDTO = restTemplate
                    .postForObject(SAVE_SUBMISSION_URL, submission, SubmissionDTO.class);

            Thread.sleep(1000);
            restTemplate.postForObject(SEND_MESSAGE_URL,
                    new MessageDTO("Thành công", 4, 1,
                            "Lưu kết quả lần nộp thành công!"), MessageDTO.class);
            return ResponseEntity.ok(submissionDTO);
        }
    }
}
