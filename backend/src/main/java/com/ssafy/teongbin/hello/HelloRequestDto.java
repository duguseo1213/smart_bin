package com.ssafy.teongbin.hello;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HelloRequestDto {
    String subject;
    String content;
}
