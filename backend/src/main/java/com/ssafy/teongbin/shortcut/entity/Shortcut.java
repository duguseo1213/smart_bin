package com.ssafy.teongbin.shortcut.entity;

import com.ssafy.teongbin.common.entity.BaseTime;
import com.ssafy.teongbin.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Entity
@Table(name = "shortcut")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Shortcut extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortcut_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private Double latitude;
    private Double longitude;

    private int zoom_level;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
