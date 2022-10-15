package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleCronjobRepository extends JpaRepository<ScheduleCronjobRunEntity, Long> {
    Page<ScheduleCronjobRunEntity> findAll(Pageable pageable);
}
