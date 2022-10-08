package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ngthotuan on 08/10/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRpDto {
    private String username;
    private String name;
    private String email;
    private List<String> roles;

    public void setRole(String role) {
        this.roles = new ArrayList<>();
        if (role != null) {
            String[] roles = role.split(",");
            this.roles.addAll(Arrays.asList(roles));
        }
    }
}
