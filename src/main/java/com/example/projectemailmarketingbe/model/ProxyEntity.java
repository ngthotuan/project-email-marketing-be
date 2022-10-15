package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProxyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proxyId;
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proxyEntity")
    private List<EmailEntity> emailEntities;
}

