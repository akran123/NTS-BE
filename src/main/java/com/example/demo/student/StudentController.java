package com.example.demo.student;


import com.example.demo.student.dto.StudentCreateDto;
import com.example.demo.student.dto.StudentResponseDto;
import com.example.demo.student.dto.StudentUpdateDto;
import com.example.demo.student.service.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 응답을 json이나 문자열 body에 보냄
@RequiredArgsConstructor  // final 또는 @NonNull이 붙은 필드를 가진 생성자를 자동으로 생성
@RequestMapping("api/student")
public class StudentController {
    private final StudentsService studentsService;

    @PostMapping("/create")
    public ResponseEntity<String> createStudent(@RequestBody StudentCreateDto dto){
        studentsService.createStudent(dto);
        return ResponseEntity.status(HttpStatus.OK).body("학생 생성 완료");
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> searchByName(@RequestParam("name") String name) {
        List<StudentResponseDto> result = studentsService.getStudents(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentUpdateDto dto) {
        Long id =dto.getId();
        studentsService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body("학생 업데이트 완료");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") Long id){ //패스 파라미터
        studentsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("학생 삭제 완료");
    }
}
