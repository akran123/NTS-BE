package com.example.demo;

import com.example.demo.user.UserRepository;
import com.example.demo.user.UserClassification;
import com.example.demo.user.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DBtest {
    @Autowired  // spring이 userRepository 객체를 자동으로 생성 및 주입( 실제 코드 에서는 생성자 사용)
    private UserRepository userRepository;

    @Test
    void testJpa(){
        Users user = new Users();
        user.setPersonal_Id("nhlk123");
        user.setPassword("12234");
        user.setUser_classification(UserClassification.PART_TIMER);
        user.setCreateDate(LocalDateTime.now());
        userRepository.save(user);

        Users result = userRepository.findById(user.getId()).orElse(null);
        System.out.println("✅ 저장된 사용자 이름: " + result.getName());
    }



}
