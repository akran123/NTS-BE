package com.example.demo.user.service;

import com.example.demo.user.UserClassification;
import com.example.demo.user.Users;
import com.example.demo.user.UsersRepository;
import com.example.demo.user.dto.UserCreateDto;
import com.example.demo.user.dto.UserRequestDto;
import com.example.demo.user.dto.UserResponseDto;
import com.example.demo.user.dto.UserUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users createUser(UserCreateDto dto) {
        Boolean b = userRepository.existsByPersonalId(dto.getPersonalId());
        if (b) {
            throw new DataIntegrityViolationException("이미 존재하는 사용자입니다.");
        }
        Users user = new Users();
        user.setPersonalId(dto.getPersonalId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setUserClassification(dto.getUserClassification());


        return userRepository.save(user);
    }

    public List<UserResponseDto> getAllUser(){
        List<Users> users = userRepository.findAll();

        if (users.isEmpty()){
            throw new EntityNotFoundException("사용자가 없습니다");
        }

        return users.stream().map(user -> new UserResponseDto(user.getId(),user.getPersonalId(),user.getPassword(),user.getName(),user.getUserClassification())).collect(Collectors.toList());

    }

    public UserResponseDto getUser(Long id){
        Users user =  userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    public Users findByPersonalId(String personalId) {
        return userRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
    }


    public UserResponseDto updateUser(UserRequestDto dto){
        Users user = userRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        user.setName(dto.getName());
        user.setPersonalId(dto.getPersonalId());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserClassification(dto.getUserClassification());
        return new UserResponseDto(user);
    }

    @Transactional // 성공해야만 db에 반영
    public void deleteUser(String name){
        if (!userRepository.existsByName(name)) {
            throw new EntityNotFoundException("삭제할 사용자가 존재하지 않음");
        }
        userRepository.deleteByName(name);
    }


    public UserClassification getUserClassification(String personalId){
        Users user = userRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        return user.getUserClassification();
    }


}
