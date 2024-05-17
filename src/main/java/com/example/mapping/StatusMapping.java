package com.example.mapping;

import com.example.model.dto.CityDto;
import com.example.model.dto.StatusDto;
import com.example.model.entity.CityEntity;
import com.example.model.entity.StatusEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface StatusMapping {

    StatusDto entityToDto(StatusEntity statusEntity);
    StatusEntity dtoToEntity(StatusDto statusDto);
    List<StatusDto> entityListToDtoList(List<StatusEntity> statusEntities);
}
