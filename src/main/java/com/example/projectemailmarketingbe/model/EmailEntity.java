package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmailEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "proxyId")
    private ProxyEntity proxyEntity;

}
