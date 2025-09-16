package com.example.demo.task;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test")
    private String test;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "todo")
    private String todo;

    @Column(name = "homework")
    private String homework;

    @Column(name = "teacher_name")
    private String teacherName;
}
