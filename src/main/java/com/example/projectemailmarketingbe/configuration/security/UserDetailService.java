package com.example.projectemailmarketingbe.configuration.security;

import com.example.projectemailmarketingbe.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailService implements UserDetails {
    private UserEntity user;

    public UserDetailService(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        //list of permission
        if (!this.user.getPermission().trim().isBlank()) {
            var permissions = user.getPermission().split(",");
            for (var permission : permissions) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission);
                list.add(grantedAuthority);
            }
        }
//         list of roles
        if (!this.user.getRole().isBlank()) {
            var roles = user.getRole().trim().split(",");
            for (var role : roles) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
                list.add(grantedAuthority);
            }
        }
        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
