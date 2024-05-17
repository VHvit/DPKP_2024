package com.example.controllers;

import com.example.model.dto.StatusDto;
import com.example.model.search.SearchStatusDto;
import com.example.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusDto>> getAllStatus() {
        List<StatusDto> status = statusService.getAllStatus();
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping
    public StatusDto createStatus(@RequestBody StatusDto statusDto) {
        return statusService.createStatus(statusDto);
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<StatusDto> updateStatus(
            @PathVariable("statusId") UUID statusId,
            @RequestBody StatusDto statusDto) {
        StatusDto updatedStatus = statusService.updateStatus(statusId, statusDto);
        return updatedStatus != null ? ResponseEntity.ok(updatedStatus) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<Void> deleteStatus(@PathVariable("statusId") UUID statusId) {
        statusService.deleteStatus(statusId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long statusCount() {
        return statusService.statusCount();
    }

    @GetMapping("/count/{statusId}")
    public int getTotalRecordCount(@PathVariable UUID statusId) {
        return statusService.getTotalRecordCount(statusId);
    }

    @GetMapping("/search")
    public SearchStatusDto searchStatus(@RequestParam String query) {
        List<StatusDto> status = statusService.searchStatus(query);
        int numberOfStatus = status.size();
        return new SearchStatusDto(numberOfStatus, status);
    }
}