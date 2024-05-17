package com.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class BranchDto {
    private UUID branchId;
    private String branchName;
    private CityDto city;
    private String address;
    private String branchPhone;
    private String employeesCount;
    private CompanyDto company;
}
