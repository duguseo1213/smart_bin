package com.ssafy.teongbin.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCategory {
    LOGIN_ERROR("로그인 에러"),
    TOKEN_ERROR("토큰 에러"),
    SIGNUP_ERROR("회원가입 에러"),
    TRASHCAN_ERROR("쓰레기통 에러"),
    RESTLOG_ERROR("잔량 로그 에러"),
    CATEGORY_ERROR("카테고리 에러"),
    EMAILVERIFY_ERROR("이메일 인증 에러"),
    SHORTCUT_ERROR("숏컷 에러");



    String category;

    ErrorCategory(String category) {
        this.category = category;
    }
}
