package com.example.mapping;

import com.example.model.dto.TypeDto;
import com.example.model.entity.TypeEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TypeMapping {

    TypeDto entityToDto(TypeEntity typeEntity);
    TypeEntity dtoToEntity(TypeDto typeDto);
    List<TypeDto> entityListToDtoList(List<TypeEntity> typeEntities);
}
