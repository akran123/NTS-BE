package com.example.demo.print;

import com.example.demo.print.dto.PageRange;
import com.example.demo.print.dto.PrintOutRequestDto;
import com.example.demo.print.dto.PrintRequestDto;
import com.example.demo.print.dto.PrintResponseDto;
import com.example.demo.print.service.PrintService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


@RestController
@RequestMapping("api/print")
@RequiredArgsConstructor
public class PrintController {

    private final PrintService printService;

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','PART_TIMER')")
    @PostMapping("/print_out")
    public ResponseEntity<String> print(@RequestBody PrintOutRequestDto request) {
        Set<Integer> pagesToPrint = new TreeSet<>();
        Map<String, List<String>> selected = request.getPrintSections();
        Map<String, Map<String, PageRange>> ranges = request.getSectionRanges();

        if (selected == null || ranges == null) {
            throw new EntityNotFoundException("선택된 단원 또는 범위가 없습니다");
        }

        for (String chapter : selected.keySet()) {
            Map<String, PageRange> chapterRange = ranges.get(chapter);
            if (chapterRange == null) continue;

            for (String sub : selected.get(chapter)) {
                PageRange range = chapterRange.get(sub);
                if (range == null) continue;

                for (int i = range.getStart(); i <= range.getEnd(); i++) {
                    pagesToPrint.add(i - 1); // 0-based index
                }
            }
        }

        File file = new File(request.getFilePath());
        if (!file.exists()) {
            throw new EntityNotFoundException("해당 교재를 찾을 수 없습니다.");
        }

        try (PDDocument doc = PDDocument.load(file);
             PDDocument newDoc = new PDDocument()) {

            int totalPages = doc.getNumberOfPages();
            for (int index : pagesToPrint) {
                if (index >= 0 && index < totalPages) {
                    newDoc.addPage(doc.getPage(index));
                }
            }

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(newDoc));

            // 인쇄 옵션 설정 (선택 사항)
            PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
            setPrintAttributes(attr, request);

            if (job.printDialog()) {
                job.print(attr);
                return ResponseEntity.ok("인쇄 완료");
            } else {
                return ResponseEntity.ok("인쇄 취소됨");
            }

        } catch (Exception e) {
            throw new EntityNotFoundException("오류가 발생했습니다.");
        }
    }

    private void setPrintAttributes(PrintRequestAttributeSet attr, PrintOutRequestDto request) {
        // 방향
        if ("LANDSCAPE".equalsIgnoreCase(request.getOrientation())) {
            attr.add(OrientationRequested.LANDSCAPE);
        } else {
            attr.add(OrientationRequested.PORTRAIT);
        }

        // 양면 여부
        switch (request.getSides() != null ? request.getSides().toUpperCase() : "") {
            case "DUPLEX":
                attr.add(Sides.DUPLEX);
                break;
            case "TUMBLE":
                attr.add(Sides.TUMBLE);
                break;
            default:
                attr.add(Sides.ONE_SIDED);
        }

        // 용지 크기
        switch (request.getPaperSize() != null ? request.getPaperSize().toUpperCase() : "") {
            case "B5":
                attr.add(MediaSizeName.ISO_B5);
                break;
            case "LETTER":
                attr.add(MediaSizeName.NA_LETTER);
                break;
            default:
                attr.add(MediaSizeName.ISO_A4);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createPrint(@RequestBody PrintRequestDto requestDto) throws Exception {
        printService.create(requestDto);
        return ResponseEntity.ok("생성완료");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<PrintResponseDto>> getAll(){
        return ResponseEntity.status(200).body(printService.getAll());
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','PART_TIMER')")
    @GetMapping("/get")
    public ResponseEntity<?> getSectionRanges(@RequestParam("name") String name) throws JsonProcessingException {
        Print print = printService.getPrint(name);

        if (print==null) {
            throw new EntityNotFoundException("해당 교재를 찾을 수 없습니다.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> ranges = objectMapper.readValue(print.getSectionRanges(), Map.class);

        return ResponseEntity.status(200).body(Map.of(
                "sectionRanges", ranges
        ));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/getbyid")
    public ResponseEntity<PrintResponseDto> getPrint1(@RequestParam("id") Long id){

        return ResponseEntity.status(200).body(printService.getPrint1(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestParam("id") Long id,
                                         @RequestBody PrintRequestDto requestDto) throws Exception {
        printService.update(id, requestDto);
        return ResponseEntity.ok("수정 완료");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Long id) {
        printService.delete(id);
        return ResponseEntity.ok("삭제 완료");
    }
}