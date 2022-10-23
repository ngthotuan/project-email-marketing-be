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
public class ProxyEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proxyId;
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
    @OneToMany(mappedBy = "proxyEntity")
    private List<EmailEntity> emailEntities;

    @PreRemove
    private void preRemove() {
        for (EmailEntity e : emailEntities) {
            e.setProxyEntity(null);
        }
    }
}

