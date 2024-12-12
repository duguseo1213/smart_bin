package com.ssafy.teongbin.common.reseponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.teongbin.common.exception.ErrorResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntityDto<T> {
    private T data;
    private String msg;
    private ErrorResponse error;

    @Builder
    public ResponseEntityDto(T data, String msg, ErrorResponse error) {
        this.data = data;
        this.msg = msg;
        this.error = error;
    }
}
