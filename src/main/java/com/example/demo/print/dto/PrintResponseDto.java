package com.example.demo.print.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrintResponseDto {

    private Long id;
    private String name;
    private String filePath;
    private Map<String, Map<String, PageRange>> sectionRanges;

}
