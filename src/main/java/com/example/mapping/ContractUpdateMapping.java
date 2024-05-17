package com.example.mapping;

import com.example.model.dto.ContractUpdateDto;
import com.example.model.dto.ContractDto;
import com.example.model.entity.ContractEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ContractUpdateMapping {

    ContractDto entityToDto(ContractEntity contractEntity);
    ContractEntity dtoToEntity(ContractUpdateDto contractDto);
}
