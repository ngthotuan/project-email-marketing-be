package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ScheduleCronjobRepository extends JpaRepository<ScheduleCronjobRunEntity, Long> {
    Page<ScheduleCronjobRunEntity> findAll(Pageable pageable);

    @Query(value = "select s.* from schedule_cronjob_run_entity s join email_entity e on s.email_id  = e.email_id " +
            "join template_enttity t on s.template_id = t.id where e.email like %:search% or t.name like %:search% ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) from schedule_cronjob_run_entity s join email_entity e on s.email_id  = e.email_id " +
                    "join template_enttity t on s.template_id = t.id where e.email like %:search% or t.name like %:search%",
            nativeQuery = true)
    Page<ScheduleCronjobRunEntity> findAll(@Param("search") String search, Pageable pageable);
}
