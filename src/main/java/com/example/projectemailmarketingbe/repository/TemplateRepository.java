package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
    @Query(value = "FROM TemplateEntity WHERE name like %:search% OR subject like %:search%",
            countQuery = "select count(t) from TemplateEntity t WHERE t.name like %:search% OR t.subject like %:search%")
    Page<TemplateEntity> findAll(@Param("search") String search, Pageable pageable);
}
