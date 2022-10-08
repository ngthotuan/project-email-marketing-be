package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ProxyEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProxyRepository extends JpaRepository<ProxyEntity, UUID> {
}
