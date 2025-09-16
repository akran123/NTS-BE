package com.example.demo.student.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCreateDto{
    private String name;
    private String grade;
    private Integer age;
    private String teacher;
}
