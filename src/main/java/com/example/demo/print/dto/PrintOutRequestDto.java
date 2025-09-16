package com.example.demo.print.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PrintOutRequestDto {

    private String name;
    private String filePath; // 예: "/pdfs/2-1/라이트쎈.pdf"
    private Map<String, List<String>> printSections; // 예: { "2단원": ["A", "B", "C"] }
    private Map<String, Map<String, PageRange>> sectionRanges; // 예: { "2단원": { "A": {start, end}, ... } }

    private String orientation; // 예: "PORTRAIT" 또는 "LANDSCAPE"
    private String sides; // 예: "ONE_SIDED", "DUPLEX", "TUMBLE"
    private String paperSize; // 예: "A4", "B5", "LETTER"
}
