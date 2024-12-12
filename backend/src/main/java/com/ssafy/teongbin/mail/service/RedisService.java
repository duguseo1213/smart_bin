package com.ssafy.teongbin.mail.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    // 인증 번호 Redis에 저장하는 코드(5는 유효시간(분))
    public void saveVerificationCode(String email, String code) {
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
    }

    // Redis에서 인증 코드를 조회하는 코드
    public String getVerificationCode(String email) {
        Object result = redisTemplate.opsForValue().get(email);
        if (result == null) {
            throw new CustomException(ErrorType.NOT_FOUND_STOREDCODE);
        }
        return (String) result;
    }

    // 인증 코드를 Redis에서 삭제하는 코드
    public void deleteVerificationCode(String email) {
        redisTemplate.delete(email);
    }
}
