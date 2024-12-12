package com.ssafy.teongbin.log.repository;

import com.ssafy.teongbin.log.entity.Restlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RestlogRepository extends JpaRepository<Restlog, Long> {
    List<Restlog> findByTrashcanId(long l);

//  time 이내 발생한 로그들만 출력 ( Ex : time = 48시간 -> 48 시간 이내 로그만 출력 )
    @Query("SELECT r FROM Restlog r WHERE r.trashcan.id = :trashcanId AND r.createdAt >= :time")
    List<Restlog> findRecentLogsByTrashcanIdAndHours(@Param("trashcanId") long trashcanId,
                                             @Param("time") LocalDateTime time);
}
