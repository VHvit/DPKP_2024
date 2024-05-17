package com.example.controllers;

import com.example.model.dto.CityDto;
import com.example.model.dto.ViewDto;
import com.example.model.search.SearchCityDto;
import com.example.model.search.SearchViewDto;
import com.example.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {

    private final ViewService viewService;


    @GetMapping
    public ResponseEntity<List<ViewDto>> getAllViews() {
        List<ViewDto> views = viewService.getAllView();
        return new ResponseEntity<>(views, HttpStatus.OK);
    }

    @PostMapping
    public ViewDto createView(@RequestBody ViewDto viewDto) {
        return viewService.createView(viewDto);
    }

    @PutMapping("/{viewId}")
    public ResponseEntity<ViewDto> updateView(
            @PathVariable("viewId") UUID viewId,
            @RequestBody ViewDto viewDto) {
        ViewDto updatedView = viewService.updateView(viewId, viewDto);
        return updatedView != null ? ResponseEntity.ok(updatedView) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{viewId}")
    public ResponseEntity<Void> deleteType(@PathVariable("viewId") UUID viewId) {
        viewService.deleteView(viewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long viewCount() {
        return viewService.viewCount();
    }

    @GetMapping("/count/{viewId}")
    public int getTotalRecordCount(@PathVariable UUID viewId) {
        return viewService.getTotalRecordCount(viewId);
    }

    @GetMapping("/search")
    public SearchViewDto searchView(@RequestParam String query) {
        List<ViewDto> view = viewService.searchView(query);
        int numberOfView = view.size();
        return new SearchViewDto(numberOfView, view);
    }
}