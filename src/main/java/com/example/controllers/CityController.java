package com.example.controllers;

import com.example.model.dto.CityDto;
import com.example.model.search.SearchCityDto;
import com.example.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cities = cityService.getAllCity();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping
    public CityDto createCity(@RequestBody CityDto cityDto) {
        return cityService.createCity(cityDto);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<CityDto> updateCity(
            @PathVariable("cityId") UUID cityId,
            @RequestBody CityDto cityDto) {
        CityDto updatedCity = cityService.updateCity(cityId, cityDto);
        return updatedCity != null ? ResponseEntity.ok(updatedCity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable("cityId") UUID cityId) {
        cityService.deleteCity(cityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long cityCount() {
        return cityService.cityCount();
    }

    @GetMapping("/count/{cityId}")
    public int getTotalRecordCount(@PathVariable UUID cityId) {
        return cityService.getTotalRecordCount(cityId);
    }

    @GetMapping("/search")
    public SearchCityDto searchCities(@RequestParam String query) {
        List<CityDto> cities = cityService.searchCities(query);
        int numberOfCities = cities.size();
        return new SearchCityDto(numberOfCities, cities);
    }

}
