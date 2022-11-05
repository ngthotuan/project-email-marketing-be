package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class TemplateEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subject;
    @Lob
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "templateEntity")
    List<FileEntity> fileEntities;

    @OneToMany(mappedBy = "templateEntity", cascade = CascadeType.ALL)
    private List<ScheduleCronjobRunEntity> scheduleCronjobRunEntities;

    @PreRemove
    public void preRemove() {
        fileEntities.forEach(fileEntity -> {
            fileEntity.setTemplateEntity(null);
        });
    }
}

