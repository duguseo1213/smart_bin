package com.ssafy.teongbin.trash.dto.request;

import lombok.Data;

import java.awt.*;

@Data
public class UpdateTrashcanRequest {
    private String nickname;

    private Double latitude;
    private Double longitude;
}
