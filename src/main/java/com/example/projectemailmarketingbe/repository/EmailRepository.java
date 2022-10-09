package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {
    Optional<EmailEntity> findByEmail(String email);

    Page<EmailEntity> findAll(Pageable pageable);

    Page<EmailEntity> findAllByEmailLike(String name, Pageable pageable);
}
