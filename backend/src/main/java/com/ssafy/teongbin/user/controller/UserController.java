package com.ssafy.teongbin.user.controller;

import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.common.reseponse.MsgType;
import com.ssafy.teongbin.common.reseponse.ResponseEntityDto;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import com.ssafy.teongbin.user.dto.request.PasswordChangeDto;
import com.ssafy.teongbin.user.dto.request.SignUpRequestDto;
import com.ssafy.teongbin.user.dto.request.UpdateUserRequestDto;
import com.ssafy.teongbin.user.dto.response.ProfileResponseDto;
import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public String signUp (@RequestBody SignUpRequestDto dto) {

        return userService.signUp(dto);
    }

    // 회원정보 조회
    @GetMapping("/profile")
    public ResponseEntityDto<ProfileResponseDto> profile(@AuthenticationPrincipal PrincipalDetails user){

        return ResponseUtils.ok(userService.profile(user), MsgType.SEARCH_SUCCESSFULLY);
    }

    // 회원정보 수정
    @PostMapping("/update")
    public ResponseEntityDto<Void> update(@AuthenticationPrincipal PrincipalDetails user,
                                          @RequestBody UpdateUserRequestDto dto ){
        userService.update(user, dto);
        return ResponseUtils.ok(MsgType.UPDATE_SUCCESSFULLY);
    }

    // 비밀번호 수정
    @PostMapping("/passwordchange")
    public ResponseEntityDto<Void> passwordchange(@RequestBody PasswordChangeDto passwordChangeDto){
        userService.passwordchange(passwordChangeDto);
        return ResponseUtils.ok(MsgType.CHANGEPASSWORD_SUCCESSFULLY);
    }
}
