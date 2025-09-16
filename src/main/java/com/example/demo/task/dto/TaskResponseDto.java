package com.example.demo.task.dto;

import com.example.demo.task.Task;
import com.example.demo.task.TaskRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponseDto{
    private Long id;
    private String test;
    private String studentName;
    private LocalDate date;
    private String time;
    private String arrivalTime;
    private String todo;
    private String homework;
    private String teacherName;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.test = task.getTest();
        this.studentName = task.getStudentName();
        this.date = task.getDate();
        this.time = task.getTime();
        this.arrivalTime = task.getArrivalTime();
        this.todo = task.getTodo();
        this.homework = task.getHomework();
        this.teacherName = task.getTeacherName();
    }
}
