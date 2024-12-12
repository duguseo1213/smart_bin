package com.ssafy.teongbin.trash.dto.response;

import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.trash.entity.Trashcan;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserLogDto {
    private List<RestDto> restPercentList; // Restlog의 rest_percent 정보

    public UserLogDto(Trashcan trashcan) {
        this.restPercentList = trashcan.getRestlogs().stream()
                .map(RestDto::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class RestDto {

        public RestDto(Restlog restlog) {
            this.restPercent = restlog.getRestPercent();
            this.trashcanId = restlog.getTrashcan().getId();
            this.createdAt = restlog.getCreatedAt();
        }
        public int restPercent;
        public Long trashcanId;
        private LocalDateTime createdAt;

    }
}
