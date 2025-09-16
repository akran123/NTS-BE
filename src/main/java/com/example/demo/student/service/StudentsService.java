package com.example.demo.student.service;

import com.example.demo.student.Students;
import com.example.demo.student.StudentsRepository;
import com.example.demo.student.dto.StudentCreateDto;
import com.example.demo.student.dto.StudentResponseDto;
import com.example.demo.student.dto.StudentUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentsService {
    private final StudentsRepository studentsRepository;

    public Students createStudent(StudentCreateDto dto) {
        Students student = new Students();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setGrade(dto.getGrade());
        student.setTeacher(dto.getTeacher());
        return studentsRepository.save(student);
    }

    public List<StudentResponseDto> getStudents(String name) {
        List<Students> students = studentsRepository.findStudentsByName(name);

        if (students.isEmpty()){
            throw new EntityNotFoundException("해당 이름의 학생이 없습니다: " + name);
        }
        return students.stream()
                .map(StudentResponseDto::new)
                .collect(Collectors.toList());
    }

    // READ (전체)
    public List<StudentResponseDto> getAll() {
        List<Students> students = studentsRepository.findAll();

        if(students.isEmpty()){
            throw new EntityNotFoundException("학생목록이 없습니다");
        }
        return students.stream()
                .map(StudentResponseDto::new)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public StudentResponseDto update(Long id, StudentUpdateDto dto) {
        Students s = studentsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("학생 없음"));

        s.setName(dto.getName());
        s.setAge(dto.getAge());
        s.setGrade(dto.getGrade());
        s.setTeacher(dto.getTeacher());
        return new StudentResponseDto(s);
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        if (!studentsRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 학생이 존재하지 않음");
        }
        studentsRepository.deleteById(id);
    }



}
