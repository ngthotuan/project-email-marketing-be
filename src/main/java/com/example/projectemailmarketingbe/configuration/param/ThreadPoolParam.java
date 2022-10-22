package com.example.projectemailmarketingbe.configuration.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPoolParam {
    @NotNull
    Integer size;
}
