package com.ssafy.teongbin.user.dto.request;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String email;
    private String password;
}
