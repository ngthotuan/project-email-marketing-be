package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.repository.ScheduleCronjobRepository;
import com.example.projectemailmarketingbe.service.ScheduleCronjobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleCronjobServiceImpl implements ScheduleCronjobService {
    private final ScheduleCronjobRepository scheduleCronjobRepository;
    @Override
    public List<ScheduleCronjobRunEntity> findAll() {
        return scheduleCronjobRepository.findAll();
    }
}
