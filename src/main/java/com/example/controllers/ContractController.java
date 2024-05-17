package com.example.controllers;

import com.example.model.dto.ContractCreateDto;
import com.example.model.dto.ContractDto;
import com.example.model.search.SearchContractDto;
import com.example.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping
    public ResponseEntity<List<ContractDto>> getAllContract() {
        List<ContractDto> cities = contractService.getAllContract();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping
    public ContractDto createContract(@RequestBody ContractCreateDto contractCreateDto) {
        return contractService.createContract(contractCreateDto);
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ContractDto> updateContract(
            @PathVariable("contractId") UUID contractId,
            @RequestBody ContractCreateDto contractCreateDto) {
        ContractDto updatedContract = contractService.updateContract(contractId, contractCreateDto);
        return updatedContract != null ? ResponseEntity.ok(updatedContract) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<Void> deleteContract(@PathVariable("contractId") UUID contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long contractCount() {
        return contractService.contractCount();
    }

    @GetMapping("/count/{contractId}")
    public int getTotalRecordCount(@PathVariable UUID contractId) {
        return contractService.getTotalRecordCount(contractId);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<SearchContractDto> getContractsByClientId(@PathVariable UUID clientId) {
        List<ContractDto> contractDtos = contractService.getContractsByClientId(clientId);

        SearchContractDto searchContractDto = new SearchContractDto();
        searchContractDto.setNumberOfContract(contractDtos.size());
        searchContractDto.setContract(contractDtos);

        return ResponseEntity.ok(searchContractDto);
    }

    @GetMapping("/search")
    public SearchContractDto getContracts(
            @RequestParam(required = false) String summ,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(required = false) UUID viewId,
            @RequestParam(required = false) UUID contractStatusId) {
        return contractService.getContracts(summ, date, viewId, contractStatusId);
    }
}