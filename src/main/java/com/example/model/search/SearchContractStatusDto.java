package com.example.model.search;

import com.example.model.dto.ContractStatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchContractStatusDto {
    private int numberOfContractStatus;
    private List<ContractStatusDto> contractStatus;
}
