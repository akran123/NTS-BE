package com.example.demo.user.dto;

import com.example.demo.user.UserClassification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor

public class UserRequestDto {
    private Long id;
    private String personalId;
    private String password;
    private String name;
    private UserClassification userClassification;

    public UserRequestDto(String personalId, String password, String name, UserClassification userClassification) {

        this.personalId = personalId;
        this.password = password;
        this.name = name;
        this.userClassification = userClassification;
    }
}
