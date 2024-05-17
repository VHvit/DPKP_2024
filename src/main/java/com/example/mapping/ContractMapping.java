package com.example.mapping;

import com.example.model.dto.ContractDto;
import com.example.model.entity.ContractEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ContractMapping {

    ContractDto entityToDto(ContractEntity contractEntity);
    ContractEntity dtoToEntity(ContractDto contractDto);
    List<ContractDto> entityListToDtoList(List<ContractEntity> contractEntities);
}
