package com.example.mapping;

import com.example.model.dto.CityDto;
import com.example.model.dto.ViewDto;
import com.example.model.entity.CityEntity;
import com.example.model.entity.ViewEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ViewMapping {

    ViewDto entityToDto(ViewEntity viewEntity);
    ViewEntity dtoToEntity(ViewDto viewDto);
    List<ViewDto> entityListToDtoList(List<ViewEntity> viewEntities);
}
