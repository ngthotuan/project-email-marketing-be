package com.example.projectemailmarketingbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProjectEmailMarketingBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectEmailMarketingBeApplication.class, args);
    }

}
