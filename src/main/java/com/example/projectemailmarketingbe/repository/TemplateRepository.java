package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
    List<TemplateDto> findAllByName(String name);
}
