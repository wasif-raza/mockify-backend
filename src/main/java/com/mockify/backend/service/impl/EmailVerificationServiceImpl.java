package com.mockify.backend.service.impl;

import com.mockify.backend.dto.request.auth.PendingRegistration;
import com.mockify.backend.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationServiceImpl
        implements EmailVerificationService {

    private static final Duration VERIFICATION_TTL =
            Duration.ofMinutes(15);

    private final RedisTemplate<String, Object> redisTemplate;

    private String key(String token) {
        return "email_verification:" + token;
    }

    @Override
    public String createVerification(
            String name,
            String email,
            String encodedPassword) {

        String token = UUID.randomUUID().toString();

        PendingRegistration pending = PendingRegistration.builder()
                .name(name)
                .email(email)
                .encodedPassword(encodedPassword)
                .build();

        redisTemplate.opsForValue().set(
                key(token),
                pending,
                VERIFICATION_TTL
        );

        log.info("Email verification created email={} token={}",
                email, token);

        return token;
    }


    @Override
    public PendingRegistration verifyAndConsume(String token) {

        String redisKey = key(token);

        Object value = redisTemplate.opsForValue().get(redisKey);

        if (value == null) {
            log.warn("Invalid or expired verification token={}", token);
            throw new IllegalArgumentException(
                    "Verification link is invalid or expired"
            );
        }

        redisTemplate.delete(redisKey);

        return (PendingRegistration) value;
    }
}