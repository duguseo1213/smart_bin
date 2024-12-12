package com.ssafy.teongbin.trash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UpdateTrashcanResponse {
    private Long id;
    private String nickname;
    private Double latitude;
    private Double longitude;
}
