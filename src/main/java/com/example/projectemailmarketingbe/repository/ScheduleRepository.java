package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ngthotuan on 15/10/2022
 */
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
