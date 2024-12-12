package com.ssafy.teongbin.common.config;

//import com.ssafy.teongbin.common.exception.CustomAuthenticationEntryPoint;
import com.ssafy.teongbin.common.jwt.JwtAuthenticationFilter;
import com.ssafy.teongbin.common.jwt.JwtAuthorizationFilter;
import com.ssafy.teongbin.common.jwt.JwtProperties;
import com.ssafy.teongbin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration; // 인증 설정
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 사용 X
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // 서버는 CORS 정책에서 벗어날 수 있다.
        http.addFilterBefore(corsFilter, JwtAuthenticationFilter.class);
        http.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProperties,
                        userRepository, "/api/v1/user/login"));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, jwtProperties));

        // form 로그인 사용하지 않는다
        http.formLogin(AbstractHttpConfigurer::disable);
        // HTTP Basic 인증 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);


        // URL 경로에 따른 접근 권한 설정
//        http.authorizeRequests(request -> {
        http.authorizeHttpRequests(request -> {
            // /api/v1/user/** 경로는 ROLE_USER, ROLE_MANAGER, ROLE_ADMIN 권한을 가진 사용자만 접근 가능
//            request.requestMatchers("/api/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')");
            // /api/v1/manager/** 경로는 ROLE_MANAGER, ROLE_ADMIN 권한을 가진 사용자만 접근 가능

            // /api/v1/admin/** 경로는 ROLE_ADMIN 권한을 가진 사용자만 접근 가능
//            request.requestMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')");
            // 루트 경로는 모든 사용자 접근 가능
            request.requestMatchers("/api/v1/user/signup", "api/v1/user/login",
                            "/static/**", "/login",
                            "/api/v1/trash/rest", "/api/v1/trash/catlog"
                            , "/api/v1/user/email"
                            , "/api/v1/user/passwordchange"
                            , "/api/v1/user/verify").permitAll()
                    .anyRequest().authenticated();
        });

        http.headers(
            headersConfigurer ->
                    headersConfigurer
                            .frameOptions(
                                    HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
        );

//        http.exceptionHandling(exceptionHandling ->
//                exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> {
            web
                    .ignoring()
                    .requestMatchers(
                            PathRequest.toStaticResources().atCommonLocations()
                    );
        };
    }
}
