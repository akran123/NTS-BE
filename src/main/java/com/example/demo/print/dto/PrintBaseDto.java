package com.example.demo.print.dto;


import lombok.Data;

import java.util.Map;

@Data
public class PrintBaseDto {
    private Long id;
    private String name;  //1-1/책이름
    private String filePath;
    private Map<String, Map<String, PageRange>> sectionRanges;


}
