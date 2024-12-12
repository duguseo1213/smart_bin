package com.ssafy.teongbin.log.controller;

import com.ssafy.teongbin.common.reseponse.MsgType;
import com.ssafy.teongbin.common.reseponse.ResponseEntityDto;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import com.ssafy.teongbin.log.dto.request.RestlogRequest;
import com.ssafy.teongbin.log.service.RestlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestlogController {
    private final RestlogService restlogService;

    @PostMapping("/api/v1/trash/rest")
    public ResponseEntityDto<Long> newRestlog(@RequestBody RestlogRequest request) {
        return ResponseUtils.ok(restlogService.join(request), MsgType.ADD_RESTLOG_SUCCESSFULLY);
    }
}
