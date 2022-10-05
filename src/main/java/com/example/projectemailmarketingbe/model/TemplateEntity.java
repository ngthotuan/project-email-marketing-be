package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TemplateEntity {
    @Id
    private UUID templateId;
    private String name;
    private String subject;
    private String cc;
    private String content;
    private String from;
}
