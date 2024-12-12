package com.ssafy.teongbin.mail.dto;

import lombok.Data;

@Data
public class ApproveRequestDto {
    private String email;
    private String code;
}
