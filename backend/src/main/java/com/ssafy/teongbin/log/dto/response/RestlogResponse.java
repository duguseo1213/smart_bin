package com.ssafy.teongbin.log.dto.response;

import lombok.Data;

@Data
public class RestlogResponse {

    private Long id;

    public RestlogResponse(Long id) {
        this.id = id;
    }
}
