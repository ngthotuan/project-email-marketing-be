package com.example.projectemailmarketingbe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
@Slf4j
public class ProjectEmailMarketingBeApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        log.info("Spring boot application running in Asia/Ho_Chi_Minh timezone :" + LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectEmailMarketingBeApplication.class, args);
    }

}
