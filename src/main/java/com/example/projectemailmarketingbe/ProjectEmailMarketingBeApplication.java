package com.example.projectemailmarketingbe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
@Slf4j
public class ProjectEmailMarketingBeApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        log.info("Spring boot application running in Europe/London timezone :" + LocalDate.now());
    }
    public static void main(String[] args) {
        SpringApplication.run(ProjectEmailMarketingBeApplication.class, args);
    }

}
