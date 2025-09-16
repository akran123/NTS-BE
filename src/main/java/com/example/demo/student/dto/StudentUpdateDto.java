package com.example.demo.student.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateDto{
    private Long id;
    private String name;
    private String grade;
    private Integer age;
    private String teacher;
}
