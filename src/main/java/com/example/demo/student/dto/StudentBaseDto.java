package com.example.demo.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // super 호출 가능
@NoArgsConstructor
public class StudentBaseDto {
    private String name;
    private String grade;
    private Integer age;
    private String teacher;
}
