package com.ssafy.teongbin.mail.controller;

import com.ssafy.teongbin.common.exception.ErrorResponse;
import com.ssafy.teongbin.common.exception.ErrorType;
import com.ssafy.teongbin.common.jwt.PrincipalDetails;
import com.ssafy.teongbin.common.reseponse.MsgType;
import com.ssafy.teongbin.common.reseponse.ResponseEntityDto;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import com.ssafy.teongbin.mail.dto.ApproveRequestDto;
import com.ssafy.teongbin.mail.dto.MailDto;
import com.ssafy.teongbin.mail.service.MailService;
import com.ssafy.teongbin.mail.service.MailVerificationService;
import com.ssafy.teongbin.mail.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MailVerificationService mailVerificationService;

    @ResponseBody
    @PostMapping("/api/v1/user/email")
    public ResponseEntityDto<Void> MailSend(@RequestBody MailDto mailDto) {
        int number = mailService.sendMail(mailDto);
        String num = String.valueOf(number);
        if (num.length() != 6 || !num.matches("\\d{6}")) {
            return ResponseUtils.error(ErrorResponse.of(ErrorType.FAILED_TO_GENERATECODE));
        } else {
            return ResponseUtils.ok(MsgType.SEND_EMAILVERIFYCOE_SUCCESSFULLY);
        }
    }

    @ResponseBody
    @PostMapping("api/v1/user/verify")
    public ResponseEntityDto<Void> verifyCode(@RequestBody ApproveRequestDto approveRequestDto) {
        boolean isVerified = mailVerificationService.verifyCode(approveRequestDto);
        if (isVerified){
            return ResponseUtils.ok(MsgType.EMAILVERIFY_SUCCESSFULLY);
        } else {
            return ResponseUtils.error(ErrorResponse.of(ErrorType.FAILED_TO_EMAILVERIFY));
        }
    }
}
