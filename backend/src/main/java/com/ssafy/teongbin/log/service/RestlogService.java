package com.ssafy.teongbin.log.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.log.dto.request.RestlogRequest;
import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.log.repository.RestlogRepository;
import com.ssafy.teongbin.trash.dto.response.UserLogDto;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.trash.repository.TrashcanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class RestlogService {
    private final RestlogRepository restlogRepository;
    private final TrashcanRepository trashcanRepository;
    private final RedisTemplate<String, Object> jsonRedisTemplate;

    public Long join(RestlogRequest request) {
        String sn = request.getSerialNumber();
        if (sn == null || sn.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_SERIAL);
        }
        Trashcan findTrashcan = trashcanRepository.findBySerialNumber(sn);
        if (findTrashcan == null) {
            throw new CustomException(ErrorType.NOT_FOUND_TRASHCAN);
        }
        Restlog restlog = new Restlog();
        restlog.setTrashcan(findTrashcan);
        Integer rp = request.getRestPercent();
        if (rp==null) {
            throw new CustomException(ErrorType.NOT_FOUND_REST);
        } else if (rp<0 || rp>100) {
            throw new CustomException(ErrorType.INVALID_REST);
        }
        restlog.setRestPercent(request.getRestPercent());
        restlogRepository.save(restlog);
        Long restlogId = restlog.getId();
        if (restlogId==null) {
            throw new CustomException(ErrorType.FAILED_TO_RESTLOG);
        }
        System.out.println(restlogId);

        // Redis에 저장
        String redisKey = "TRASHCAN_LOGS_" + findTrashcan.getId();
        if (Boolean.TRUE.equals(jsonRedisTemplate.hasKey(redisKey))) {
            List<UserLogDto.RestDto> cachedLogs = (List<UserLogDto.RestDto>) jsonRedisTemplate.opsForValue().get(redisKey);
            if (cachedLogs != null) {
                UserLogDto.RestDto restDto = new UserLogDto.RestDto(restlog);
                cachedLogs.add(restDto);
                System.out.println(restDto);
                jsonRedisTemplate.opsForValue().set(redisKey, cachedLogs, 3, TimeUnit.DAYS);
            }
        }

        return restlogId;
    }
}