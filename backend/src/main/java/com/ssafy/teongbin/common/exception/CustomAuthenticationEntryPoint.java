package com.ssafy.teongbin.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.teongbin.common.reseponse.ResponseEntityDto;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//
//        ErrorResponse errorResponse;
//        System.out.println(authException.getMessage());
//        // 기본적으로 NOT_VALID_TOKEN 오류로 처리
//        errorResponse = ErrorType.NOT_VALID_TOKEN.toErrorResponse(authException.getMessage());
//
//        ResponseEntityDto<Void> responseEntityDto =
//                ResponseUtils.error(errorResponse);
//
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(convertObjectToJson(responseEntityDto));
//    }
//
//    private String convertObjectToJson(Object object) throws IOException {
//        if (object == null) {
//            return null;
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(object);
//    }
//}
//
