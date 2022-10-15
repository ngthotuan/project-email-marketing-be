package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyRepository extends JpaRepository<ProxyEntity, Long> {
    @Query(value = "FROM ProxyEntity WHERE name like %:search% OR username like %:search% OR host like %:search%", countQuery = "from ProxyEntity")
    Page<ProxyEntity> findAll(@Param("search") String search, Pageable pageable);
}
