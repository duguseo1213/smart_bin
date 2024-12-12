package com.ssafy.teongbin.shortcut.dto.request;

import lombok.Data;


@Data
public class AddShortcutRequestDto {
    String nickname;
    Double latitude;
    Double longitude;
    int zoom_level;

}
