package com.ssafy.teongbin.log.entity;

import com.ssafy.teongbin.common.entity.BaseTime;
import com.ssafy.teongbin.trash.entity.Trashcan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categorylog")
public class Catlog extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categorylog_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trashcan_id")
    private Trashcan trashcan;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
