package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyRepository extends JpaRepository<ProxyEntity, Long> {
}
