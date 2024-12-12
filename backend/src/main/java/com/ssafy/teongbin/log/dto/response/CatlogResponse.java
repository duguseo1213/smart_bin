package com.ssafy.teongbin.log.dto.response;

import lombok.Data;

@Data
public class CatlogResponse {
    public CatlogResponse(Long id) {
        this.id = id;
    }

    private Long id;

}
