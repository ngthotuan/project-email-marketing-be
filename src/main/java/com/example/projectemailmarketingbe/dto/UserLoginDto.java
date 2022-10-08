package com.example.projectemailmarketingbe.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserLoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
