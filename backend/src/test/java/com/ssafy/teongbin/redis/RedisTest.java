package com.ssafy.teongbin.redis;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.log.repository.RestlogRepository;
import com.ssafy.teongbin.trash.dto.response.UserLogDto;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.trash.repository.TrashcanRepository;
import com.ssafy.teongbin.trash.service.UserTrashcanService;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RedisTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrashcanRepository trashcanRepository;

    @Autowired
    private RedisTemplate<String, Object> jsonRedisTemplate;

    @Autowired
    private UserTrashcanService userTrashcanService;

    @BeforeEach
    void setUp() {
        // 각 테스트 전 설정할 작업이 있다면 여기서 설정
    }

    @Test
    @Order(1)
    void testUserRestLogWithCaching() {
        // 테스트 실행 전 Redis 초기화
        jsonRedisTemplate.getConnectionFactory().getConnection().flushAll();

        // Assuming the database already has a user with ID 9
        Optional<User> ou = userRepository.findById(9L);
        assertTrue(ou.isPresent());
        User user = ou.get();

        PrincipalDetails userIn = new PrincipalDetails(user);
        List<Trashcan> trashcans = trashcanRepository.findByUser(user);
        assertFalse(trashcans.isEmpty());

        long startTime = System.currentTimeMillis();
        List<UserLogDto.RestDto> result = userTrashcanService.userRestLogWithCaching(userIn);
        long endTime = System.currentTimeMillis();

        System.out.println("Test 1: " + (endTime - startTime) + " ms");

        assertNotNull(result);
    }

    @Test
    @Order(2)
    void testUserRestlogV2() {
        // Assuming the database already has a user with ID 9
        Optional<User> ou = userRepository.findById(9L);
        assertTrue(ou.isPresent());

        User user = ou.get();
        List<Trashcan> trashcans = trashcanRepository.findByUser(user);
        assertFalse(trashcans.isEmpty());

        PrincipalDetails userIn = new PrincipalDetails(user);

        long startTime = System.currentTimeMillis();
        List<UserLogDto.RestDto> result = userTrashcanService.userRestlogV2(userIn);
        long endTime = System.currentTimeMillis();

        System.out.println("Test 2: " + (endTime - startTime) + " ms");

        assertNotNull(result);
        // Additional assertions based on expected data in the database
    }
}