package com.example.projectemailmarketingbe;

import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.repository.ScheduleRepository;
import com.example.projectemailmarketingbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

        // init email
        if (emailRepository.count() == 0) {
            log.info("init email");
            List<EmailEntity> emails = LongStream.range(0, 100)
                    .mapToObj(i -> {
                        EmailEntity email = new EmailEntity();
                        email.setEmail(RandomString.make() + "@gmail.com");
                        email.setPassword(RandomString.make());
                        return email;
                    })
                    .collect(Collectors.toList());
            emailRepository.saveAll(emails);
        }

        // init schedule
        if (scheduleRepository.count() == 0) {
            log.info("init schedule");
            scheduleRepository.save(new ScheduleEntity(1L, "Run every minute", "0 0/1 * * * ?"));
            scheduleRepository.save(new ScheduleEntity(2L, "At 9:30", "0 30 9 * * ?"));
            scheduleRepository.save(new ScheduleEntity(3L, "At 12:00 AM, only on Sunday and Saturday", "0 0 0 ? * SUN,SAT"));
        }
    }
}
