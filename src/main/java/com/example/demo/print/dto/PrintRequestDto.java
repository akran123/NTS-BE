package com.example.demo.print.dto;


import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Data
public class PrintRequestDto {
    private Long id;
    private String name;
    private String filePath;

    private Map<String, Map<String, PageRange>> sectionRanges; // 전체 단원 범위
}
