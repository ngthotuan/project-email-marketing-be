package com.example.projectemailmarketingbe;

import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by ngthotuan on 09/10/2022
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final ScheduleRepository scheduleRepository;
    private final ProxyRepository proxyRepository;
    private final TemplateRepository templateRepository;
    private final ScheduleCronjobRepository scheduleCronjobRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // init user
        if (userRepository.count() == 0) {
            log.info("init user");
            UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                    .username("emtest")
                    .password("emtest")
                    .name("EM Test")
                    .email("em@gmail.com")
                    .build();
            UserEntity user = modelMapper.map(userRegisterDto, UserEntity.class);
            user.setRole("admin");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

        // init schedule
        if (scheduleRepository.count() == 0) {
            log.info("init schedule");
            scheduleRepository.save(ScheduleEntity.builder().id(1L).name("Run every 5 minute").cron("0 0/5 * * * ?").build());
            scheduleRepository.save(ScheduleEntity.builder().id(2L).name("At 9:30").cron("0 30 9 * * ?").build());
            scheduleRepository.save(ScheduleEntity.builder().id(3L).name("At 12:00 AM, only on Sunday and Saturday").cron("0 0 0 ? * SUN,SAT").build());
        }

//        // init proxy vs email
//        if (proxyRepository.count() == 0 && emailRepository.count() == 0) {
//            log.info("init proxy vs email");
//            List<EmailEntity> emailsWithProxy = LongStream.range(0, 100)
//                    .mapToObj(i -> EmailEntity.builder()
//                            .email(RandomString.make() + "@gmail.com")
//                            .password(RandomString.make())
//                            .proxyEntity(ProxyEntity.builder()
//                                    .name(RandomString.make())
//                                    .host(RandomString.make())
//                                    .port(String.format("%06d", i))
//                                    .username(RandomString.make())
//                                    .password(RandomString.make())
//                                    .build())
//                            .build()
//                    ).collect(Collectors.toList());
//            emailRepository.saveAll(emailsWithProxy);
//        }
//
//        // init template
//        if (templateRepository.count() == 0) {
//            log.info("init template");
//            List<TemplateEntity> templates = LongStream.range(0, 100)
//                    .mapToObj(i -> TemplateEntity.builder()
//                            .name(RandomString.make())
//                            .subject(RandomString.make())
//                            .content(RandomString.make())
//                            .build()
//                    ).collect(Collectors.toList());
//            templateRepository.saveAll(templates);
//        }
//
//        // init schedule cronjob
//        if (scheduleCronjobRepository.count() == 0) {
//            log.info("init schedule cronjob");
//            List<ScheduleCronjobRunEntity> scheduleCronjobRunEntities = LongStream.range(0, 100)
//                    .mapToObj(i -> ScheduleCronjobRunEntity.builder()
//                            .emailEntity(emailRepository.findById(i + 1).get())
//                            .templateEntity(templateRepository.findById(i + 1).get())
//                            .scheduleEntity(scheduleRepository.findById(i % 3 + 1).get())
//                            .emailTo(LongStream.range(0, ThreadLocalRandom.current().nextInt(1, 6))
//                                    .mapToObj(j -> RandomString.make() + "@gmail.com")
//                                    .collect(Collectors.joining(",")))
//                            .enable(true)
//                            .build()
//                    ).collect(Collectors.toList());
//            scheduleCronjobRepository.saveAll(scheduleCronjobRunEntities);
//        }
    }
}
