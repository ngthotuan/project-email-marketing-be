package com.example.projectemailmarketingbe.filter;

import com.example.projectemailmarketingbe.configuration.security.UserDetailServiceImpl;
import com.example.projectemailmarketingbe.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("/api-docs/") && !request.getRequestURI().contains("/users/")) {
            final String requestTokenHeader = request.getHeader("Authorization");
            String username = "";

            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                String jwtToken = requestTokenHeader.substring(7);

                username = jwtUtils.getUsernameFromToken(jwtToken);
            }

            // Once we get the token validate it.
            if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}