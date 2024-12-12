package com.ssafy.teongbin.trash.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.teongbin.common.entity.BaseTime;
import com.ssafy.teongbin.log.entity.Catlog;
import com.ssafy.teongbin.log.entity.Restlog;
import com.ssafy.teongbin.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trashcan")
public class Trashcan extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trashcan_id")
    private Long id;

    @Column(unique = true)
    private String serialNumber;


    private String nickname;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trashcan",cascade = CascadeType.ALL)
    private List<Catlog> catlogs = new ArrayList<>();

    @OneToMany(mappedBy = "trashcan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Restlog> restlogs = new ArrayList<>();
}
