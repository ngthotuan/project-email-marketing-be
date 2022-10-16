package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ngthotuan on 15/10/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticDto {
    private long schedule;
    private long email;
    private long proxy;
    private long template;
    private long scheduleCron;
}
