package com.example.demo.task.service;

import com.example.demo.task.Task;
import com.example.demo.task.TaskRepository;
import com.example.demo.task.dto.TaskCreateDto;
import com.example.demo.task.dto.TaskResponseDto;
import com.example.demo.task.dto.TaskUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    // 여러 Task 저장
    public List<Task> saveAll(List<Task> tasks) {
        return taskRepository.saveAll(tasks);
    }

    public List<TaskResponseDto> getTasksByDateAndTeacherName(String teacherName, LocalDate date) {

        List<Task> tasks = taskRepository.findByDateAndTeacherName(date, teacherName);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("과제가 없습니다");
        }
            return tasks.stream()
                    .map(TaskResponseDto::new)
                    .collect(Collectors.toList());

    }

    // 특정 교사 이름으로 Task 목록 조회
    public List<TaskResponseDto> getTasksByTeacherName(String teacherName) {
        List<Task> tasks = taskRepository.findByTeacherName(teacherName);

        if(tasks.isEmpty()){
            throw new EntityNotFoundException("해당 선생님의 과제가 없습니다");
        }
        return tasks.stream()
                .map(TaskResponseDto::new)
                .collect(Collectors.toList());
    }

    public Task createTask(TaskCreateDto taskCreateDto) {
        Task task = new Task();
        task.setTest(taskCreateDto.getTest());
        task.setStudentName(taskCreateDto.getStudentName());
        task.setDate(taskCreateDto.getDate());
        task.setTime(taskCreateDto.getTime());
        task.setArrivalTime(taskCreateDto.getArrivalTime());
        task.setTodo(taskCreateDto.getTodo());
        task.setHomework(taskCreateDto.getHomework());
        task.setTeacherName(taskCreateDto.getTeacherName());

        return taskRepository.save(task);
    }

    //과제 업데이트
    public TaskResponseDto updateTask(Long id, TaskUpdateDto task){
        Task t=taskRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("과제 없음"));
        t.setTest(task.getTest());
        t.setTime(task.getTime());
        t.setDate(task.getDate());
        t.setArrivalTime(task.getArrivalTime());
        t.setHomework(task.getHomework());
        t.setStudentName(task.getStudentName());
        t.setTeacherName(task.getTeacherName());
        t.setTodo(task.getTodo());
        return new TaskResponseDto(t);
    }


    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 과제가 존재하지 않음");
        }
        taskRepository.deleteById(id);

    }
}
