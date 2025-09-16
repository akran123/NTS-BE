package com.example.demo.student;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String name;

    @Column(length = 20)
    private String grade;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 20,nullable = false)
    private String teacher;


}
