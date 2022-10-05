package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProxyEntity {
    @Id
    private UUID proxyId;
    private String username;
    private String password;
    private String host;
    private String port;
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proxyEntity")
    private List<EmailEntity> emailEntities;
}

