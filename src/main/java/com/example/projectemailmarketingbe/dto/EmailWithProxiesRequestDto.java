package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailWithProxiesRequestDto {
    private List<EmailWithProxyDto> emailWithProxyDtos;
}
