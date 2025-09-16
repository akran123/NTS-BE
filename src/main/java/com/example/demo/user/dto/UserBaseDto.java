package com.example.demo.user.dto;

import com.example.demo.user.UserClassification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBaseDto {
    private String personalId;
    private String password;
    private String name;
    private UserClassification userClassification;
}


