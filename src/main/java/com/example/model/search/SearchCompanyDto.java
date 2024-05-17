package com.example.model.search;

import com.example.model.dto.CompanyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompanyDto {
    private int numberOfCompany;
    private List<CompanyDto> company;
}
