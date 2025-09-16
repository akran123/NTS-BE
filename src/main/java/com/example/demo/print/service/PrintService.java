package com.example.demo.print.service;


import com.example.demo.print.PrintRepository;
import com.example.demo.print.dto.PageRange;
import com.example.demo.print.dto.PrintBaseDto;
import com.example.demo.print.Print;
import com.example.demo.print.dto.PrintRequestDto;
import com.example.demo.print.dto.PrintResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrintService {

    private final PrintRepository repository;
    private final ObjectMapper objectMapper;

    private Map<String, Map<String, PageRange>> parseSectionRanges(String json) {
        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("sectionRanges JSON 파싱 실패", e);
        }
    }

    public void create(PrintRequestDto dto) throws Exception {
        String name = dto.getName();

        // 이미 존재하면 예외
        if (repository.findByName(name).isPresent()) {
            throw new DataIntegrityViolationException("이미 존재합니다: " + name);
        }

        // JSON 직렬화
        String json = objectMapper.writeValueAsString(dto.getSectionRanges());

        Print entity = Print.builder()
                .name(dto.getName())
                .filePath(dto.getFilePath())
                .sectionRanges(json)
                .build();

        repository.save(entity);
    }


    public PrintResponseDto getPrint1(Long id){
        Print print = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("파일이 없습니다"));
        Map<String, Map<String, PageRange>> sectionRanges = parseSectionRanges(print.getSectionRanges());
        PrintResponseDto entity = new PrintResponseDto(print.getId(), print.getName(), print.getFilePath(), sectionRanges);

        return entity;
    }


    public Print getPrint(String name){
        Print print = repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("파일이 없습니다"));

        return print;
    }

    public List<PrintResponseDto> getAll() {

        List<Print> prints = repository.findAll();
        if (prints == null){
            throw new EntityNotFoundException("데이터가 없습니다.");
        }

        return prints.stream()
                .map(print -> {
                    Map<String, Map<String, PageRange>> sectionRanges = parseSectionRanges(print.getSectionRanges());
                    return new PrintResponseDto(
                            print.getId(),
                            print.getName(),
                            print.getFilePath(),
                            sectionRanges
                    );
                })
                .collect(Collectors.toList());
        }


    public void update(Long id, PrintRequestDto dto) throws Exception {
        Print entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("파일이 없습니다"));

        entity.setName(dto.getName());
        entity.setFilePath(dto.getFilePath());
        entity.setSectionRanges(objectMapper.writeValueAsString(dto.getSectionRanges()));

        repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
