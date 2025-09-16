package com.example.demo;

import com.example.demo.user.UserClassification;
import com.example.demo.user.Users;
import com.example.demo.user.UsersRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final String SECRET_KEY = "yoursecret1241adlfgaflagskeyasdfqwerasdfqwer123451512";
    private final long EXPIRATION_MS = 1000 * 60 * 60; // 1시간
    private Key key;
    private final UsersRepository usersRepository;


    public String generateToken(String personalId, UserClassification userClassification) {
        return Jwts.builder()
                .setSubject(personalId)
                .claim("role", userClassification.toString()) // 역할군 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256) // key
                .compact();
    }
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    }

    public Authentication getAuthentication(String token) {
        try {
            String personalId = getPersonalIdFromToken(token);
            Users user = usersRepository.findByPersonalId(personalId)
                    .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

            Claims claims = getAllClaims(token);
            String role = claims.get("role", String.class);

            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(user.getPersonalId())
                    .password(user.getPassword())
                    .authorities(new SimpleGrantedAuthority("ROLE_"+role))
                    .build();


            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 확인
            throw e;
        }
    }


    public String getPersonalIdFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getUserClassificationFromToken(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    //  5. HTTP 요청에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
