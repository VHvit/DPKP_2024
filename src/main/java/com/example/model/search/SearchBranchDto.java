package com.example.model.search;

import com.example.model.dto.BranchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBranchDto {
    private int numberOfBranch;
    private List<BranchDto> branch;
}
