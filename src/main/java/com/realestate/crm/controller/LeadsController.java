package com.realestate.crm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.crm.model.Leads;
import com.realestate.crm.model.SampleData;
import com.realestate.crm.repository.LeadsRepository;
import com.realestate.crm.repository.SampleDataRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/Leads")
@CrossOrigin(origins = "http://localhost:8100")
public class LeadsController {

	@Autowired
    private LeadsRepository repository;

    // CREATE
    @PostMapping
    public Leads create(@RequestBody Leads sampleData) {
        return repository.save(sampleData);
    }

    // READ ALL
    @GetMapping
    public List<Leads> getAll() {
        return repository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Leads> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Leads> update(@PathVariable Long id, @RequestBody Leads updated) {
        return repository.findById(id)
                .map(existing -> {
                    updated.setId(id);
                    return ResponseEntity.ok(repository.save(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // UPLOAD EXCEL
    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<Leads> siteVisits = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Leads visit = new Leads();
                visit.setFirstVisit(getCellValue(row.getCell(0)));
                visit.setSiteVisitDate(parseDate(row.getCell(1), formatter));
                visit.setContactName(getCellValue(row.getCell(2)));
                visit.setContact(getCellValue(row.getCell(3)));
                visit.setEmail(getCellValue(row.getCell(4)));
                visit.setNationality(getCellValue(row.getCell(5)));
                visit.setAgeGroup(getCellValue(row.getCell(6)));
                visit.setEthnicity(getCellValue(row.getCell(7)));
                visit.setEmploymentType(getCellValue(row.getCell(8)));
                visit.setCompanyName(getCellValue(row.getCell(9)));
                visit.setOfficeLocality(getCellValue(row.getCell(10)));
                visit.setPincode(getCellValue(row.getCell(11)));
                visit.setIndustry(getCellValue(row.getCell(12)));
                visit.setAddress1(getCellValue(row.getCell(13)));
                visit.setLocality(getCellValue(row.getCell(14)));
                visit.setUnitType(getCellValue(row.getCell(15)));
                visit.setBudget(getCellValue(row.getCell(16)));
                visit.setAreaSqFeet(getCellValue(row.getCell(17)));
                visit.setConstructionStatus(getCellValue(row.getCell(18)));
                visit.setSeekingFor(getCellValue(row.getCell(19)));

                siteVisits.add(visit);
            }

            repository.saveAll(siteVisits);
            return ResponseEntity.ok("Uploaded " + siteVisits.size() + " records successfully.");
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? cell.getDateCellValue().toString()
                    : String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private LocalDate parseDate(Cell cell, DateTimeFormatter formatter) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            }

            String value = getCellValue(cell).trim();
            if (value.isEmpty()) return null; // 💥 THIS IS THE FIX

            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            // Optional: log invalid date warning
            return null;
        }
    }

}
