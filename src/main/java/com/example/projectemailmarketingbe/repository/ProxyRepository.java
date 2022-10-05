package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProxyRepository extends JpaRepository<ProxyEntity, UUID> {
}
