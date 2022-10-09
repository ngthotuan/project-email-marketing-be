package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProxyEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID proxyId;
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proxyEntity")
    private List<EmailEntity> emailEntities;
}

