package com.service.submittask_service.helper;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
public final class Util {

    public String multipartFileReader(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return new String(fileContent, StandardCharsets.UTF_8);
    }

    public String failedStatusConverter(String str) {
        return switch (str) {
            case "RUNTIME_ERROR"  -> "RTE";
            case "TIMED_OUT"      ->  "TLE";
            case "INTERNAL_ERROR" -> "CE";
            case "OUT_OF_MEMORY"  -> "MLE";
            default -> null;
        };
    }

    public boolean hasValidExtension(MultipartFile file, String language) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("c", "c");
        hm.put("cpp", "c++");
        hm.put("java", "java");
        hm.put("py", "python");

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return hm.containsKey(fileExtension) && hm.get(fileExtension).equals(language);
    }

    public String getLanguage(Integer language) {
        return switch (language) {
            case 1 -> "c";
            case 2 -> "c++";
            case 3 -> "java";
            case 4 -> "python";
            default -> null;
        };
    }
}
