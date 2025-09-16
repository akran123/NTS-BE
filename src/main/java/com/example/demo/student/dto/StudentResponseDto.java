package com.example.demo.student.dto;

import com.example.demo.student.Students;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponseDto extends StudentBaseDto {
    private Long id;
    private String name;
    private String grade;
    private Integer age;
    private String teacher;

    // 생성자 추가
    public StudentResponseDto(Students student) {
        this.id = student.getId();
        this.name = student.getName();
        this.grade = student.getGrade();
        this.age = student.getAge();
        this.teacher = student.getTeacher();
    }
}