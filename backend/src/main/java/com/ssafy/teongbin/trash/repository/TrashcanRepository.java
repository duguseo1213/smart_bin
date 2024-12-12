package com.ssafy.teongbin.trash.repository;

import com.ssafy.teongbin.trash.dto.response.UserLogDto;
import com.ssafy.teongbin.trash.dto.response.UserTrashcanDto;
import com.ssafy.teongbin.trash.dto.response.UserTrashcanRestDtoV2;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrashcanRepository extends JpaRepository<Trashcan, Long> {
    Trashcan findBySerialNumber(String serialNumber);

    List<Trashcan> findByUser(User user);

    @Query("SELECT new com.ssafy.teongbin.trash.dto.response.UserTrashcanRestDtoV2(t.id, " +
            "COALESCE((SELECT r.restPercent FROM Restlog r WHERE r.trashcan.id = t.id AND r.createdAt = " +
            "(SELECT MAX(r2.createdAt) FROM Restlog r2 WHERE r2.trashcan.id = t.id)), 0) " +  // COALESCE로 null 처리
            ") FROM Trashcan t WHERE t.user = :user")
    List<UserTrashcanRestDtoV2> findTrashcanRestDtoV2ByUser(@Param("user") User user);

    @Query("SELECT new com.ssafy.teongbin.trash.dto.response.UserLogDto$RestDto(r) " +
            "FROM Restlog r " +
            "WHERE r.trashcan.id IN :trashcanIds " +
            "AND r.createdAt >= :nowMinus3 " +
            "ORDER BY r.createdAt DESC")
    List<UserLogDto.RestDto> findRestDto(@Param("trashcanIds") List<Long> trashcanIds, @Param("nowMinus3") LocalDateTime nowMinus3);

    @Query("SELECT new com.ssafy.teongbin.trash.dto.response.UserTrashcanDto(t) From Trashcan t WHERE t.user = :user")
    List<UserTrashcanDto> findTrashcanDtosByUser(@Param("user") User user);

    @Query("SELECT t FROM Trashcan t JOIN FETCH t.catlogs WHERE t.id = :trashcanId")
    Optional<Trashcan> findTrashcanById(@Param("trashcanId") Long trashcanId);

}
