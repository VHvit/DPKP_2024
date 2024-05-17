package com.example.model.search;

import com.example.model.dto.ContractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchContractDto {
    private int numberOfContract;
    private List<ContractDto> contract;
}
