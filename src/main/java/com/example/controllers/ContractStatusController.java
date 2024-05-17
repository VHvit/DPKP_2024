package com.example.controllers;

import com.example.model.dto.ContractStatusDto;
import com.example.model.search.SearchContractStatusDto;
import com.example.service.ContractStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contractStatus")
@RequiredArgsConstructor
public class ContractStatusController {

    private final ContractStatusService contractStatusService;

    @GetMapping
    public ResponseEntity<List<ContractStatusDto>> getAllContractStatus() {
        List<ContractStatusDto> contractStatus = contractStatusService.getAllContractStatus();
        return new ResponseEntity<>(contractStatus, HttpStatus.OK);
    }

    @PostMapping
    public ContractStatusDto createContractStatus(@RequestBody ContractStatusDto contractStatusDto) {
        return contractStatusService.createContractStatus(contractStatusDto);
    }

    @PutMapping("/{contractStatusId}")
    public ResponseEntity<ContractStatusDto> updateContractStatus(
            @PathVariable("contractStatusId") UUID contractStatusId,
            @RequestBody ContractStatusDto contractStatusDto) {
        ContractStatusDto updatedContractStatus = contractStatusService.updateContractStatus(contractStatusId, contractStatusDto);
        return updatedContractStatus != null ? ResponseEntity.ok(updatedContractStatus) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{contractStatusId}")
    public ResponseEntity<Void> deleteContractStatus(@PathVariable("contractStatusId") UUID contractStatusId) {
        contractStatusService.deleteContractStatus(contractStatusId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long contractStatusCount() {
        return contractStatusService.contractStatusCount();
    }

    @GetMapping("/count/{contractStatusId}")
    public int getTotalRecordCount(@PathVariable UUID contractStatusId) {
        return contractStatusService.getTotalRecordCount(contractStatusId);
    }

    @GetMapping("/search")
    public SearchContractStatusDto searchContractStatus(@RequestParam String query) {
        List<ContractStatusDto> contractStatus = contractStatusService.searchContractStatus(query);
        int numberOfContractStatus = contractStatus.size();
        return new SearchContractStatusDto(numberOfContractStatus, contractStatus);
    }
}
