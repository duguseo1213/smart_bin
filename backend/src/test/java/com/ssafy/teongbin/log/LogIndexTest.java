package com.ssafy.teongbin.log;

import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.log.repository.RestlogRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LogIndexTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RestlogRepository restlogRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INDEX_NAME = "idx_trashcan_id";

    @BeforeEach
    public void setup() {
        // 필요한 경우 여기에서 테스트 데이터를 초기화합니다.
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testWithoutIndex() {
        // 인덱스 사용하지 않음
        jdbcTemplate.execute("SET SESSION optimizer_switch='index_merge=off';");
        long startTime = System.nanoTime();
        List<Restlog> restlogs = restlogRepository.findByTrashcanId(1L);
        long endTime = System.nanoTime();
        System.out.println("Without Index - Execution time: " + (endTime - startTime) / 1000000 + " ms");
        assertNotNull(restlogs);
        jdbcTemplate.execute("SET SESSION optimizer_switch='index_merge=on';"); // 원래 설정 복원
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testWithIndex() {
        // 인덱스 사용
        long startTime = System.nanoTime();
        List<Restlog> restlogs = restlogRepository.findByTrashcanId(1L);
        long endTime = System.nanoTime();
        System.out.println("With Index - Execution time: " + (endTime - startTime) / 1000000 + " ms");
        assertNotNull(restlogs);
    }
}