package com.ssafy.teongbin.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorResponse;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import com.ssafy.teongbin.user.dto.request.LoginRequestDto;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    // 커스텀 로그인 URL 설정
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties, UserRepository userRepository, String loginUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
        this.userRepository = userRepository;
        setFilterProcessesUrl(loginUrl); // 로그인 URL 설정
    }

    // /login 요청을 하면 로그인 시도를 하기 위해서 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response )
            throws AuthenticationException {
//        System.out.println("JWTAuthenticationFilter : 로그인 시도 중");
        // email, password 받아서
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
//        try {
//        // json 데이터 파싱
//            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
//        // 변경된 부분: 로그인 요청 DTO가 올바르게 파싱되었는지 로그를 추가
////            System.out.println("Parsed loginRequestDto: " + loginRequestDto.getPassword());
//        } catch (IOException e) {
//            handleException(response, new CustomException(ErrorType.CONTENT_IS_NULL));
//            return null;
//        }
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (IOException e) {
            try {
                handleException(response, new CustomException(ErrorType.CONTENT_IS_NULL));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }

        // loginRequestDto가 null인 경우 명확한 예외 발생
//        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null) {
//            handleException(response, new CustomException(ErrorType.CONTENT_IS_NULL));
//            return null;
//        }
        if (loginRequestDto.getEmail() == null || loginRequestDto.getPassword() == null) {
            try {
                handleException(response, new CustomException(ErrorType.CONTENT_IS_NULL));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }

        // User가 DB에 존재하는지 확인
//        User user = userRepository.findByEmail(loginRequestDto.getEmail())
//                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        User user;
        try {
            user = userRepository.findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        } catch (CustomException e) {
            try {
                handleException(response, e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }

        // 토큰 만들기
        UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // PrincipalDetailsService의 loadUserByEmail() 함수가 실행됨
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 다운 캐스팅, authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 뜻
//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        System.out.println("로그인 완료 : " + principalDetails.getUser().getEmail());
//
//        return authentication;
        try {
            return authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            try {
                handleException(response, new CustomException(ErrorType.NOT_MATCHING_INFO));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }

        // 정상인지 로그인 시도를 해보는 것
        // authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService가 호출 loadUserByUsername() 함수가 실행
        // PrincipalDetails를 세션에 담고(권한 관리를 위해서) JWT 토큰을 만들고 응답해주면 됨

    }

    // attemptAuthentication 실행 => 인증이 정상적으로 되었다면 successfulAuthentication 실행
    // JWT 토큰을 만들어서 request 요청을 한 사용자에게 JWT 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
//        System.out.println("successfulAuthentication 실행됨 : 인증 완료");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식이 아니라 Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject("Access Token") // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis()+Integer.parseInt(jwtProperties.EXPIRATION_TIME))) // 토큰 만료 시간 => 현재 시간 + 10분
                .withClaim("id", principalDetails.getUser().getId())  // 비공개 claim, 내가 넣고 싶은 value값
                .withClaim("email", principalDetails.getUser().getEmail())
                .sign(Algorithm.HMAC512(jwtProperties.SECRET));  // 내 서버가 아는 고유의 값

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 사용자한테 응답할 response 헤더에
        response.addHeader(jwtProperties.HEADER_STRING, jwtProperties.TOKEN_PREFIX+jwtToken);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("data", "Bearer " + jwtToken);
        responseBody.put("msg", "로그인 성공");

        // ObjectMapper를 사용하여 HashMap을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        // 사용자에게 응답으로 JSON 문자열 전송
        response.getWriter().write(jsonResponse);
//        response.getWriter().write("{\"message\": \"로그인 성공\", \"token\": \"\n" + "Bearer " + jwtToken + "\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // CustomException이 발생한 경우
        if (failed.getCause() instanceof CustomException) {
            handleException(response, (CustomException) failed.getCause());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ErrorResponse responseDto = ErrorResponse.of(ErrorType.NOT_VALID_TOKEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtils.error(responseDto)));
        }
    }

    private void handleException(HttpServletResponse response, CustomException e) throws IOException {
        response.setStatus(e.getErrorType().getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse responseDto = ErrorResponse.of(e.getErrorType());
        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseUtils.error(responseDto)));
//        System.out.println("Error handled: " + e.getMessage());
    }

}