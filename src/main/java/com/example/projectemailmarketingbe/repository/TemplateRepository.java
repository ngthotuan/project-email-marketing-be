package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateRepository extends JpaRepository<TemplateEntity, UUID> {
}
