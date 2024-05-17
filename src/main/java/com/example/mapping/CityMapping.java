package com.example.mapping;

import com.example.model.dto.CityDto;
import com.example.model.entity.CityEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface CityMapping {

    CityDto entityToDto(CityEntity cityEntity);
    CityEntity dtoToEntity(CityDto cityDto);

    List<CityDto> entityListToDtoList(List<CityEntity> cityEntities);
}
