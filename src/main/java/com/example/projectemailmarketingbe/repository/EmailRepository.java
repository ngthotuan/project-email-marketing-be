package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.EmailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    Optional<EmailEntity> findByEmail(String email);

    Page<EmailEntity> findAll(Pageable pageable);

    @Query(value = "from EmailEntity where email like %:search% or emailName like %:search%",
            countQuery = "select COUNT(p) from EmailEntity p where p.email like %:search% or p.emailName like %:search%")
    Page<EmailEntity> findAllByEmailAndEmailName(String search, Pageable pageable);
}
