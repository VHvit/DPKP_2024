package com.example.model.search;

import com.example.model.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchCityDto {
    private int numberOfCities;
    private List<CityDto> city;
}
