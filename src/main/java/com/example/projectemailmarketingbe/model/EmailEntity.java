package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmailEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID emailId;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "proxyId")
    private ProxyEntity proxyEntity;
}
