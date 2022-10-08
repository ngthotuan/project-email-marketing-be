package com.example.projectemailmarketingbe.configuration.security;

import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.projectemailmarketingbe.constant.MessageConstant.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        UserEntity user = userOpt.orElseThrow(() -> {
            log.error(USER_NOT_FOUND, username);
            return new NotFoundException(String.format("User not found %s", username));
        });

        return new UserDetailService(user);
    }
}
