package com.ssafy.teongbin.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorResponse;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith(jwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equals("/api/v1/user/login")
                || request.getRequestURI().equals("/api/v1/user/signup")
                || request.getRequestURI().equals("/api/v1/trash/rest")
                || request.getRequestURI().equals("/api/v1/trash/catlog")
                || request.getRequestURI().equals("/api/v1/trash/passwordchange")
                || request.getRequestURI().equals("/api/v1/user/email")
                || request.getRequestURI().equals("/api/v1/user/verify")) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(jwtProperties.HEADER_STRING).replace(jwtProperties.TOKEN_PREFIX, "");
        try {
            String email = JWT.require(Algorithm.HMAC512(jwtProperties.SECRET)).build().verify(jwtToken)
                    .getClaim("email").asString();

            if (email != null) {
                Optional<User> userEntity = userRepository.findByEmail(email);
                if (userEntity.isEmpty()) throw new CustomException(ErrorType.NOT_VALID_TOKEN);

                PrincipalDetails principalDetails = new PrincipalDetails(userEntity.get());
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new CustomException(ErrorType.NOT_VALID_TOKEN);
            }
        } catch (TokenExpiredException e) {
            handleException(response, new CustomException(ErrorType.EXPIRED_TOKEN));
            return;
        } catch (CustomException e) {
            handleException(response, e);
            return; // 예외 처리 후 필터 체인을 중단합니다.
        }

        chain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, CustomException e) throws IOException {
        response.setStatus(e.getErrorType().getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse responseDto = ErrorResponse.of(e.getErrorType());
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
//        System.out.println("Error handled: " + e.getMessage());
    }
}
