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
public class ScheduleCronjobRunEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne()
    @JoinColumn(name = "email_id")
    private EmailEntity emailEntity;

    private String emailTo;

    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity scheduleEntity;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity templateEntity;
}
