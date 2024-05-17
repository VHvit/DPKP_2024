package com.example.model.search;

import com.example.model.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchStatusDto {
    private int numberOfStatus;
    private List<StatusDto> status;
}
