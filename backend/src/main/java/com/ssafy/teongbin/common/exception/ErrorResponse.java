package com.ssafy.teongbin.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

    private String category;
    private int status;
    private String msg;

    @Builder
    public ErrorResponse(int status, String msg, String category) {
        this.category = category;
        this.status = status;
        this.msg = msg;
    }

    public static ErrorResponse of(ErrorType errorType) {
        return ErrorResponse.builder()
                .category(errorType.getCategory())
                .status(errorType.getCode())
                .msg(errorType.getMsg())
                .build();
    }

    public static ErrorResponse of(String msg, int status, String category) {
        return ErrorResponse.builder()
                .category(category)
                .status(status)
                .msg(msg)
                .build();
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        String message = "";

        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .msg(message)
                .category("Validation")
                .build();
    }
}
