package com.ssafy.teongbin.trash.dto.request;

import lombok.Data;


@Data
public class NewTrashcanRequest {

    private String serialNumber;
    private String nickname;

    private Double latitude;
    private Double longitude;

}
