package com.example.controllers;

import com.example.model.dto.CompanyDto;
import com.example.model.search.SearchBranchDto;
import com.example.model.search.SearchCompanyDto;
import com.example.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompany() {
        List<CompanyDto> company = companyService.getAllCompany();
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
        return companyService.createCompany(companyDto);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDto> updateCompany(
            @PathVariable("companyId") UUID companyId,
            @RequestBody CompanyDto companyDto) {
        CompanyDto updatedCompany = companyService.updateCompany(companyId, companyDto);
        return updatedCompany != null ? ResponseEntity.ok(updatedCompany) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("companyId") UUID companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long companyCount() {
        return companyService.companyCount();
    }

    @GetMapping("/count/{companyId}")
    public int getTotalRecordCount(@PathVariable UUID companyId) {
        return companyService.getTotalRecordCount(companyId);
    }

    @GetMapping("/search")
    public SearchCompanyDto getCompany(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) UUID cityId) {
        return companyService.getCompany(companyName, cityId);
    }
}
