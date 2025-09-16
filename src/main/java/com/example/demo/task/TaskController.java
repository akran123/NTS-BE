package com.example.demo.task;

import com.example.demo.task.dto.TaskCreateDto;
import com.example.demo.task.dto.TaskResponseDto;
import com.example.demo.task.dto.TaskUpdateDto;
import com.example.demo.task.service.TaskService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/task")

public class TaskController {

    private final TaskService taskService;

    // 리스트 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveAllTasks(@RequestBody List<Task> tasks) {

        return ResponseEntity.ok("과제 저장 완료");
    }



    //과제 생성
    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskCreateDto task) {
        taskService.createTask(task);
        return ResponseEntity.ok("과제 생성 완료");
    }

    // 특정 학생 + 날짜 조회
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/get_tasks")
    public ResponseEntity<List<TaskResponseDto>> getTasksByTeacherName(@RequestParam("name") String name,@RequestParam("date") LocalDate date)
     {
        return ResponseEntity.status(200).body(taskService.getTasksByDateAndTeacherName(name,date));
    }
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<TaskResponseDto> updateTask(@RequestParam("id") Long id, @RequestBody TaskUpdateDto task){
        return ResponseEntity.status(200).body(taskService.updateTask(id,task));
    }
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @GetMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestParam("id") Long id){
        taskService.delete(id);
        return ResponseEntity.ok("삭제완료");
    }

}
