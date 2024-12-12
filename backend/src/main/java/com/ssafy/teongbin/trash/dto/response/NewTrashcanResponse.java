package com.ssafy.teongbin.trash.dto.response;

import lombok.Data;

@Data
public class NewTrashcanResponse {

    private Long id;

    public NewTrashcanResponse(Long id) {
        this.id = id;
    }
}
