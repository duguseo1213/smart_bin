package com.ssafy.teongbin.user.entity;

import com.ssafy.teongbin.common.entity.BaseTime;
import com.ssafy.teongbin.shortcut.entity.Shortcut;
import com.ssafy.teongbin.trash.entity.Trashcan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private List<String> roleList = new ArrayList<>();
    private String password;

    // 기본 지도 정보
    private Double latitude = 35.20389;
    private Double longitude = 126.8069;
    private int zoom_level = 15;

    @OneToMany(mappedBy = "user")
    private List<Trashcan> Trashcans = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Shortcut> Shortcuts = new ArrayList<>();

    @Builder
    private User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void addUserRole(String Role) {
        this.roleList.add("ROLE_USER");
    }

    public void deleteUserRole() {
        this.roleList.remove("ROLE_USER");
    }

    public void addAdminRole(String Role) {
        this.roleList.add("ROLE_ADMIN");
    }

    public void deleteAdminRole() {
        this.roleList.remove("ROLE_ADMIN");
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void updateLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void updateZoomLevel(int zoom_level) {
        this.zoom_level = zoom_level;
    }

}
