package com.example.demo.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskUpdateDto {
    private String test;
    private String studentName;
    private LocalDate date;
    private String time;
    private String arrivalTime;
    private String todo;
    private String homework;
    private String teacherName;
}
