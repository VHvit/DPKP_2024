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

public class CompanyDto {

    private UUID companyId;
    private String  companyName;
    private String  licenseYear;
    private CityDto  city;
    private String  phone;
    private TypeDto  type;
}
