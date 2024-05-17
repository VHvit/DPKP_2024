package com.example.mapping;

import com.example.model.dto.ContractStatusDto;
import com.example.model.entity.ContractStatusEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ContractStatusMapping {

    ContractStatusDto entityToDto(ContractStatusEntity contractStatusEntity);
    ContractStatusEntity dtoToEntity(ContractStatusDto contractStatusDto);
    List<ContractStatusDto> entityListToDtoList(List<ContractStatusEntity> contractStatusEntities);
}
