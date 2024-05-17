package com.example.mapping;

import com.example.model.dto.ContractCreateDto;
import com.example.model.dto.ContractDto;
import com.example.model.entity.ContractEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface ContractCreateMapping {

    ContractDto entityToDto(ContractEntity contractEntity);
    ContractEntity dtoToEntity(ContractCreateDto contractDto);
}
