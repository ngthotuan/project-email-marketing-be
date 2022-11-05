package com.example.projectemailmarketingbe.repository;

import com.example.projectemailmarketingbe.model.FileEntity;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
//    @Query(value = "select count() from FileEntity f join TemplateEntity t where t.id = ?1 and f.name = ?2")
//    Integer findByNameAndTemplateId(Long templateId, String name);

    Optional<FileEntity> findByName(String name);

    List<FileEntity> findByTemplateEntity(TemplateEntity templateEntity);
}
