package org.hackaton.max2025.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.auth.exception.MaxValidationException;
import org.hackaton.max2025.auth.model.AuthResponse;
import org.hackaton.max2025.auth.service.AuthService;
import org.hackaton.max2025.auth.service.util.MaxValidationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${max.bot-token}")
    private String maxBotToken;

    public AuthResponse authenticate(String initData) {
        Long userId = MaxValidationUtil.validateAndExtractUserId(initData, maxBotToken);
        // TODO: Временно - все USER
        String role = "USER";

        String token = generateJWT(userId, role);

        return new AuthResponse(token, userId, role);
    }

    private String generateJWT(Long userId, String role) {
        return JWT.create()
                .withSubject(userId.toString())
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}