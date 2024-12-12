package com.ssafy.teongbin.log.controller;

import com.ssafy.teongbin.common.reseponse.MsgType;
import com.ssafy.teongbin.common.reseponse.ResponseEntityDto;
import com.ssafy.teongbin.common.reseponse.ResponseUtils;
import com.ssafy.teongbin.log.dto.request.CatlogRequest;
import com.ssafy.teongbin.log.service.CatlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CatlogController {

    private final CatlogService catlogService;

    @PostMapping("/api/v1/trash/catlog")
    public ResponseEntityDto<Long> newCatlog(@RequestBody CatlogRequest request) {
        return ResponseUtils.ok(catlogService.join(request),MsgType.ADD_CATLOG_SUCCESSFULLY);
    }
}
