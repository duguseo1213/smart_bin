package com.ssafy.teongbin.trash.dto.response;

import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.trash.entity.Trashcan;
import com.ssafy.teongbin.user.entity.User;
import lombok.Data;


@Data
public class UserTrashcanDto {

    private Long trashcanId;
    private String serialNumber;
    private String nickname;
    private Double latitude;
    private Double longitude;
    private int restPercent;

    public UserTrashcanDto(Trashcan trashcan) {
        this.trashcanId = trashcan.getId();
        this.serialNumber = trashcan.getSerialNumber();
        this.nickname = trashcan.getNickname();
        this.latitude = trashcan.getLatitude();
        this.longitude = trashcan.getLongitude();
        this.restPercent = trashcan.getRestlogs().stream()
                .map(Restlog::getRestPercent)
                .reduce((first, second) -> second)
                .orElse(0);
    }

}
