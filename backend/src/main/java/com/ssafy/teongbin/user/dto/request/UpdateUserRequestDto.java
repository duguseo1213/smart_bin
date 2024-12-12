package com.ssafy.teongbin.user.dto.request;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UpdateUserRequestDto {
    String name;
    String password;
    Double latitude;
    Double longitude;
    Integer zoom_level;
}
