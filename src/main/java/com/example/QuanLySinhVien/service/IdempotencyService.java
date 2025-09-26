package com.example.QuanLySinhVien.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final Duration TTL = Duration.ofHours(24); // key sống 24h

    public boolean exists(String key){
        return stringRedisTemplate.hasKey("idem: " + key);
    }

    public void saveResponse(String key, String responseJson){
        stringRedisTemplate.opsForValue().set("idem: " + key, responseJson, TTL);
    }

    public String getResponse(String key){
        return stringRedisTemplate.opsForValue().get("idem: " + key);
    }
}
