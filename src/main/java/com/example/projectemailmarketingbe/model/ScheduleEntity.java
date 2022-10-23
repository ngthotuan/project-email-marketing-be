package com.example.projectemailmarketingbe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ngthotuan on 13/10/2022
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ScheduleEntity extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cron;

    @OneToMany(mappedBy = "scheduleEntity", cascade = CascadeType.ALL)
    private List<ScheduleCronjobRunEntity> scheduleCronjobRunEntities;
}
