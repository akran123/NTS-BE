package com.example.demo.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter//setter는 일반적으로 넣지 않음, 데이터 직접 변경 가능한 setter 메서드는 좋지 않음
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,unique = true)
    private String personalId;

    @Column()
    private String password;

    @Column(length = 10)
    private String name;

    @Enumerated(EnumType.STRING) // enum그대로를 문자열로 저장
    private UserClassification userClassification;



}
