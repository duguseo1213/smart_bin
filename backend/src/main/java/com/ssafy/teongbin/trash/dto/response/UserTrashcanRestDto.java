package com.ssafy.teongbin.trash.dto.response;

import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.trash.entity.Trashcan;
import lombok.Data;

@Data
public class UserTrashcanRestDto {
    public UserTrashcanRestDto(Trashcan trashcan) {
        this.trashcanId = trashcan.getId();
        this.restPercent = trashcan.getRestlogs().stream()
                .map(Restlog::getRestPercent)
                .reduce((first, second) -> second)
                .orElse(0);
    }
    private Long trashcanId;
    private int restPercent;
}
