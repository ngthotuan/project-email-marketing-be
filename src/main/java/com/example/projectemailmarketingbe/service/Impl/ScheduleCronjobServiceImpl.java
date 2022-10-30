package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ScheduleCronjobDto;
import com.example.projectemailmarketingbe.dto.ScheduleCronjobRpDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.ScheduleCronjobMapper;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import com.example.projectemailmarketingbe.repository.ScheduleCronjobRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import com.example.projectemailmarketingbe.service.ScheduleCronjobService;
import com.example.projectemailmarketingbe.service.ScheduleService;
import com.example.projectemailmarketingbe.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.SCHEDULE_CRONJOB_RUN_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ScheduleCronjobServiceImpl implements ScheduleCronjobService {
    private final ScheduleCronjobRepository scheduleCronjobRepository;
    private final TemplateService templateService;
    private final EmailService emailService;
    private final ScheduleService scheduleService;
    private final ScheduleCronjobMapper scheduleCronjobMapper;

    @Override
    public List<ScheduleCronjobRunEntity> findAll() {
        return scheduleCronjobRepository.findAll(Sort.by("createdDate").descending());
    }

    @Override
    public PageResponseDto<ScheduleCronjobRpDto> findAllWithPagingAndSearch(String search, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ScheduleCronjobRunEntity> pageEmail = search.isBlank()
                ? scheduleCronjobRepository.findAll(pageable)
                : scheduleCronjobRepository.findAll((Specification<ScheduleCronjobRunEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            final Path<EmailEntity> emailPath = root.get("emailEntity");
            final Path<TemplateEntity> templatePath = root.get("templateEntity");
            final Path<ScheduleEntity> schedulePath = root.get("scheduleEntity");
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(emailPath.get("email"), "%" + search + "%")));
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(emailPath.get("emailName"), "%" + search + "%")));
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(templatePath.get("name"), "%" + search + "%")));
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(schedulePath.get("name"), "%" + search + "%")));
            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        PageResponseDto pageResponseDto = PageResponseDto
                .builder()
                .page(page)
                .size(size)
                .totalPages(pageEmail.getTotalPages())
                .totalElements(pageEmail.getTotalElements())
                .elements(pageEmail.stream()
                        .map(scheduleCronjobMapper::ScheduleCronjobRunEntityToRpDto)
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    @Transactional
    public ScheduleCronjobRpDto updateProxy(ScheduleCronjobDto scheduleCronjobDto) {
        TemplateEntity templateEntity = templateService.findByIdRPEntity(scheduleCronjobDto.getTemplateId());
        EmailEntity emailEntity = emailService.findEmailByEmailReturnEntity(scheduleCronjobDto.getEmail());
        ScheduleEntity scheduleEntity = scheduleService.findById(scheduleCronjobDto.getScheduleId());
        ScheduleCronjobRunEntity scheduleCronjobRunEntity = scheduleCronjobRepository.findById(scheduleCronjobDto.getId()).orElseThrow(() -> new NotFoundException(SCHEDULE_CRONJOB_RUN_NOT_FOUND));
        scheduleCronjobRunEntity.setScheduleEntity(scheduleEntity);
        scheduleCronjobRunEntity.setEmailEntity(emailEntity);
        scheduleCronjobRunEntity.setTemplateEntity(templateEntity);
        scheduleCronjobRunEntity.setEmailTo(scheduleCronjobDto.getEmailTos());
        return scheduleCronjobMapper.ScheduleCronjobRunEntityToRpDto(scheduleCronjobRunEntity);
    }

    @Override
    public void deleteScheduleCronjobById(Long scheduleCronjobId) {
        ScheduleCronjobRunEntity scheduleCronjobRunEntity = scheduleCronjobRepository.findById(scheduleCronjobId).orElseThrow(() -> new NotFoundException(SCHEDULE_CRONJOB_RUN_NOT_FOUND));
        scheduleCronjobRepository.delete(scheduleCronjobRunEntity);
    }

    @Override
    public ScheduleCronjobRpDto findScheduleCronjobById(Long scheduleCronjobId) {
        ScheduleCronjobRunEntity scheduleCronjobRunEntity = scheduleCronjobRepository.findById(scheduleCronjobId).orElseThrow(() -> new NotFoundException(SCHEDULE_CRONJOB_RUN_NOT_FOUND));
        return scheduleCronjobMapper.ScheduleCronjobRunEntityToRpDto(scheduleCronjobRunEntity);
    }

    @Override
    public ScheduleCronjobRpDto addScheduleCronjob(ScheduleCronjobDto scheduleCronjobDto) {
        TemplateEntity templateEntity = templateService.findByIdRPEntity(scheduleCronjobDto.getTemplateId());
        EmailEntity emailEntity = emailService.findEmailByEmailReturnEntity(scheduleCronjobDto.getEmail());
        ScheduleEntity scheduleEntity = scheduleService.findById(scheduleCronjobDto.getScheduleId());
        ScheduleCronjobRunEntity scheduleCronjobRunEntity = new ScheduleCronjobRunEntity();
        scheduleCronjobRunEntity.setScheduleEntity(scheduleEntity);
        scheduleCronjobRunEntity.setEmailEntity(emailEntity);
        scheduleCronjobRunEntity.setTemplateEntity(templateEntity);
        scheduleCronjobRunEntity.setEmailTo(scheduleCronjobDto.getEmailTos());
        scheduleCronjobRepository.save(scheduleCronjobRunEntity);
        return scheduleCronjobMapper.ScheduleCronjobRunEntityToRpDto(scheduleCronjobRunEntity);
    }

    @Override
    @Transactional
    public ScheduleCronjobRpDto updateStatusCronjob(Long scheduleCronjobId, Boolean enable) {
        ScheduleCronjobRunEntity scheduleCronjobRunEntity = scheduleCronjobRepository.findById(scheduleCronjobId).orElseThrow(() -> new NotFoundException(SCHEDULE_CRONJOB_RUN_NOT_FOUND));
        scheduleCronjobRunEntity.setEnable(enable);
        return scheduleCronjobMapper.ScheduleCronjobRunEntityToRpDto(scheduleCronjobRunEntity);
    }
}
