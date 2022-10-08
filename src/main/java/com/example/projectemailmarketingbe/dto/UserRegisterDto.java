package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {
//        @NotBlank(message = "Username cannot be blank")
        private String username;
//        @NotBlank(message = "Password cannot be blank")
        private String password;
//        @Email(message = "Email is not valid")
        private String email;
//        @NotBlank(message = "Name cannot be blank")
        private String name;
}
