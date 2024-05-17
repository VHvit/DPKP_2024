package com.example.model.search;

import com.example.model.dto.ViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchViewDto {
    private int numberOfView;
    private List<ViewDto> view;
}
