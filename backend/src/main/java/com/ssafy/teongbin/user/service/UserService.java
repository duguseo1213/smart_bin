package com.ssafy.teongbin.user.service;

import com.ssafy.teongbin.common.exception.CustomException;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.user.dto.request.PasswordChangeDto;
import com.ssafy.teongbin.user.dto.request.SignUpRequestDto;
import com.ssafy.teongbin.user.dto.request.UpdateUserRequestDto;
import com.ssafy.teongbin.user.dto.response.ProfileResponseDto;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public String signUp (@RequestBody SignUpRequestDto dto) {
        if ( userRepository.findByEmail(dto.getEmail()).isEmpty() ){
            User user = User.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .name(dto.getName())
                    .build();
            userRepository.saveAndFlush(user);
        }
        else{
            throw new CustomException(ErrorType.DUPLICATED_USERID);
        }
        return "등록 성공";
    }

    // 회원 정보 조회
    public ProfileResponseDto profile (PrincipalDetails user){
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if (ou.isPresent()) {
            User us = ou.get();
            return ProfileResponseDto.builder()
                    .email(us.getEmail())
                    .name(us.getName())
                    .latitude(us.getLatitude())
                    .longitude(us.getLongitude())
                    .zoom_level(us.getZoom_level())
                    .build();
        } else {
            throw new CustomException(ErrorType.NOT_FOUND_USER);
        }
    }

    // 회원 정보 수정
    public void update (PrincipalDetails user, UpdateUserRequestDto dto ){
        Optional<User> ou = userRepository.findByEmail(user.getUsername());
        if ( ou.isEmpty() )
            throw new CustomException(ErrorType.NOT_FOUND_USER);

        User target = ou.get();

        // name 수정
        if ( dto.getName() != null && !dto.getName().isEmpty() ){
            target.updateName(dto.getName());
        }

        // password 수정
        if ( dto.getPassword() != null && !dto.getPassword().isEmpty() ){
            String enc_pw = passwordEncoder.encode(dto.getPassword());
            target.updatePassword(enc_pw);
        }

        // latitude 수정
        if ( dto.getLatitude() != null ){
            target.updateLatitude(dto.getLatitude());
        }

        // longitude 수정
        if ( dto.getLongitude() != null ){
            target.updateLongitude(dto.getLongitude());
        }

        // zoom_level 수정
        if ( dto.getZoom_level() != null ) {
            target.updateZoomLevel(dto.getZoom_level());
        }

        userRepository.save(ou.get());
    }

    public void passwordchange(PasswordChangeDto passwordChangeDto) {
        Optional<User> ou = userRepository.findByEmail(passwordChangeDto.getEmail());

        if ( ou.isEmpty() )
            throw new CustomException(ErrorType.NOT_FOUND_USER);

        User target = ou.get();
        // password 수정
        if ( passwordChangeDto.getPassword() != null && !passwordChangeDto.getPassword().isEmpty() ){
            String enc_pw = passwordEncoder.encode(passwordChangeDto.getPassword());
            target.updatePassword(enc_pw);
            userRepository.save(ou.get());
        } else {
            throw new CustomException(ErrorType.NOT_FOUND_NEW_PASSWORD);
        }
    }
}
