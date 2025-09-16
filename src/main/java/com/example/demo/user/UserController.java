package com.example.demo.user;

import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserRequestDto;
import com.example.demo.user.dto.UserResponseDto;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // 응답을 json이나 문자열 body에 보냄
@RequiredArgsConstructor  // final 또는 @NonNull이 붙은 필드를 가진 생성자를 자동으로 생성
@RequestMapping("api/user") // 공통경로
public class UserController {
    private final UsersService usersService;

    //유저 생성
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createUserRe(@RequestBody UserCreateDto dto){

        usersService.createUser(dto);
        return ResponseEntity.ok("회원 생성 완료");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get")
    public ResponseEntity<UserResponseDto> getUser(@RequestParam("id") Long id ){
        UserResponseDto dto = usersService.getUser(id);
        return ResponseEntity.status(200).body(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.status(200).body(usersService.getAllUser());

    }
    //유저 업데이트
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDto dto){
        usersService.updateUser(dto);
        return ResponseEntity.ok("업데이트 완료");
    }

    //유저 삭제
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("name") String name){
        usersService.deleteUser(name);
        return ResponseEntity.ok("삭제 완료");
    }
}
