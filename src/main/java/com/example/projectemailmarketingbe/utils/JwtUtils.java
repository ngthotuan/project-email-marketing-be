package com.example.projectemailmarketingbe.utils;


import com.example.projectemailmarketingbe.exception.ExpiredException;
import com.example.projectemailmarketingbe.model.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
@Component
public class JwtUtils {
    @Value("${com.example.demo.jwt.expired_token}")
    private long expiredToken;
    @Value("${com.example.demo.jwt.expired_refresh_token}")
    private long expiredRefreshToken;
    @Value("${com.example.demo.jwt.issuer}")
    private String issuer;
    @Value("${com.example.demo.jwt.secret}")
    private String secretKey;


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        if (claims == null) {
            throw new RuntimeException("Token is empty");
        }
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build().parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            throw new ExpiredException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
        return claims;
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username, this.expiredToken);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username, this.expiredRefreshToken);
    }
    //test

    public List<UserEntity> getObjectFromToken(String token, String name) {
        return (List<UserEntity>) Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token).getBody().get(name);
    }

    public String generateAccessTokenObject(UserEntity object) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", object);
        return doGenerateToken(claims, object.getUsername(), this.expiredToken);
    }

    public String getObjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    private String doGenerateToken(Map<String, Object> claims, String subject, long expiredToken) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuer(this.issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredToken * 1000))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    //validate token
    public Boolean validateToken(String token, String username) {
        String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(username) && !isTokenExpired(token));
    }
}