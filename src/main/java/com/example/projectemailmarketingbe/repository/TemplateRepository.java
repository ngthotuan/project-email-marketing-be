package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
    @Query(value = "FROM TemplateEntity WHERE name like %:search% OR subject like %:search%", countQuery = "from ProxyEntity")
    Page<TemplateEntity> findAll(@Param("search") String search, Pageable pageable);
}
