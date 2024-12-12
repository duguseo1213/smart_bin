package com.ssafy.teongbin.shortcut.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ShortcutResponseDto {

    private Long shortcut_id;
    private String nickname;

    private Double latitude;
    private Double longitude;
    private int zoom_level;


    public static ShortcutResponseDto fromEntity(Long shortcut_id, String nickname, Double latitude, Double longitude, int zoom_level) {
        return new ShortcutResponseDto(shortcut_id, nickname, latitude, longitude, zoom_level);
    }


}
