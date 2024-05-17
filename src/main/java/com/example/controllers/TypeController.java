package com.example.controllers;

import com.example.model.dto.TypeDto;
import com.example.model.dto.ViewDto;
import com.example.model.search.SearchTypeDto;
import com.example.model.search.SearchViewDto;
import com.example.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public ResponseEntity<List<TypeDto>> getAllTypes() {
        List<TypeDto> types = typeService.getAllType();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @PostMapping
    public TypeDto createType(@RequestBody TypeDto typeDto) {
        return typeService.createType(typeDto);
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<TypeDto> updateType(
            @PathVariable("typeId") UUID typeId,
            @RequestBody TypeDto typeDto) {
        TypeDto updatedType = typeService.updateType(typeId, typeDto);
        return updatedType != null ? ResponseEntity.ok(updatedType) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<Void> deleteType(@PathVariable("typeId") UUID typeId) {
        typeService.deleteType(typeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long typeCount() {
        return typeService.typeCount();
    }

    @GetMapping("/count/{typeId}")
    public int getTotalRecordCount(@PathVariable UUID typeId) {
        return typeService.getTotalRecordCount(typeId);
    }

    @GetMapping("/search")
    public SearchTypeDto searchType(@RequestParam String query) {
        List<TypeDto> type = typeService.searchType(query);
        int numberOfType = type.size();
        return new SearchTypeDto(numberOfType, type);
    }
}