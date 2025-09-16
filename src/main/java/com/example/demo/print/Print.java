package com.example.demo.print;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Print {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String filePath;


    @Column(columnDefinition = "json") // MySQL
    private String sectionRanges; // JSON 문자열
}


