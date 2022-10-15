package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;

import java.util.List;

public interface ScheduleCronjobService {
    List<ScheduleCronjobRunEntity> findAll();
}
