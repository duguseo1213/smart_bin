package com.ssafy.teongbin.user.dto.response;

import lombok.Data;

@Data
public class NewUserResponse {
    public NewUserResponse(Long id) {
        this.id = id;
    }

    private Long id;

}
