package com.example.model.search;

import com.example.model.dto.TypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchTypeDto {
    private int numberOfType;
    private List<TypeDto> type;
}
