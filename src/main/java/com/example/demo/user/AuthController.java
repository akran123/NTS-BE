package com.example.demo.user;

import com.example.demo.JwtTokenProvider;
import com.example.demo.user.dto.LoginRequestDto;
import com.example.demo.user.dto.LoginResponseDto;
import com.example.demo.user.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UsersService usersService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        Users user = usersService.findByPersonalId(request.getPersonalId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserClassification userClassification = user.getUserClassification();
        String token = jwtTokenProvider.generateToken(user.getPersonalId(), userClassification);

        LoginResponseDto response = new LoginResponseDto(token, userClassification.toString());

        return ResponseEntity.ok(response);
    }


}
