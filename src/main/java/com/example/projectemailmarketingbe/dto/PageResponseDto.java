package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResponseDto<T> {
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
    List<T> elements;
}
