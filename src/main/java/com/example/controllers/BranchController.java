package com.example.controllers;

import com.example.model.dto.BranchDto;
import com.example.model.search.SearchBranchDto;
import com.example.model.search.SearchContractDto;
import com.example.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllCities() {
        List<BranchDto> cities = branchService.getAllBranch();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping
    public BranchDto createBranch(@RequestBody BranchDto branchDto) {
        return branchService.createBranch(branchDto);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<BranchDto> updateBranch(
            @PathVariable("branchId") UUID branchId,
            @RequestBody BranchDto branchDto) {
        BranchDto updatedBranch = branchService.updateBranch(branchId, branchDto);
        return updatedBranch != null ? ResponseEntity.ok(updatedBranch) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable("branchId") UUID branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long branchCount() {
        return branchService.branchCount();
    }

    @GetMapping("/count/{branchId}")
    public int getTotalRecordCount(@PathVariable UUID branchId) {
        return branchService.getTotalRecordCount(branchId);
    }

    @GetMapping("/search")
    public SearchBranchDto getBranch(
            @RequestParam(required = false) String branchName,
            @RequestParam(required = false) UUID cityId,
            @RequestParam(required = false) UUID companyId) {
        return branchService.getBranch(branchName, cityId, companyId);
    }
}
