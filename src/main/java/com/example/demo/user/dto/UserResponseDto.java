package com.example.demo.user.dto;

import com.example.demo.user.UserClassification;
import com.example.demo.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto{
    private Long id;
    private String personalId;
    private String password;
    private String name;
    private UserClassification userClassification;

    public UserResponseDto(Users user) {
        this.personalId = user.getPersonalId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.userClassification = user.getUserClassification();
        this.id = user.getId();
    }
}
