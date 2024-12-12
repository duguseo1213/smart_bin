package com.ssafy.teongbin.common.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.SECRET}")
    String SECRET;

    @Value("${jwt.EXPIRATION_TIME}")
    String EXPIRATION_TIME;

    @Value("${jwt.TOKEN_PREFIX}")
    String TOKEN_PREFIX;

    @Value("${jwt.HEADER_STRING}")
    String HEADER_STRING;
}