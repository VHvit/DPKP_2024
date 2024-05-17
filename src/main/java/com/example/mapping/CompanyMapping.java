package com.example.mapping;

import com.example.model.dto.CompanyDto;
import com.example.model.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface CompanyMapping {

    CompanyDto entityToDto(CompanyEntity companyEntity);
    CompanyEntity dtoToEntity(CompanyDto companyDto);
}
