package com.invoice.web.infrastructure.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.web.infrastructure.Constants;
import com.invoice.web.persistence.model.User;
import com.invoice.web.persistence.repositories.SystemConfigRepository;
import com.invoice.web.persistence.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final UserRepository userRepository;
    private final SystemConfigRepository systemConfigRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtUtil(UserRepository userRepository, SystemConfigRepository systemConfigRepository) {
        this.userRepository = userRepository;
        this.systemConfigRepository = systemConfigRepository;
    }

    public String generateToken(String email) {
        User user = userRepository.findByEmail(email);

        Claims claims = Jwts.claims();
        claims.put("name", user.getName());
        claims.put("surname", user.getSurname());
        claims.put("email", user.getEmail());
        claims.put("phone", user.getPhone());
        claims.put("country", user.getCountry());
        claims.put("department", user.getDepartment());
        claims.put("role", user.getRole());

        String SECRET_KEY = systemConfigRepository.findBySystemKey(Constants.SECRET_KEY_JWT_TOKEN).getSystemValue();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
