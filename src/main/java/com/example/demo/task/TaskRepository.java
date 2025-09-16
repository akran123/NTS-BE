package com.example.demo.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByTeacherName(String name);
    List<Task> findByDate(LocalDate date);
    List<Task> findByDateAndTeacherName(LocalDate date, String teacherName);
}
