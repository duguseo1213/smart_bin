package com.ssafy.teongbin.hello;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hello {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hello_id")
    Long id;

    @Column(nullable = false)
    String subject;

    String content;

    @Builder
    public Hello(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
