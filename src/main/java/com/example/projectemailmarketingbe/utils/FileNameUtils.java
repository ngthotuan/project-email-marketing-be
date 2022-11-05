package com.example.projectemailmarketingbe.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileNameUtils {
    public String createFileNameUnique(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        String extend = fileName.substring(lastIndexOf);
        String name = fileName.substring(0, lastIndexOf);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = currentDateTime.format(formatter);
        String key = formattedDateTime.replaceAll(":", "_");
        return String.format("%s_%s%s", name, key, extend);
    }
}
