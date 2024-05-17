package com.example.mapping;

import com.example.model.dto.ClientDto;
import com.example.model.dto.ContractDto;
import com.example.model.entity.ClientEntity;
import com.example.model.entity.ContractEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ClientMapping {

    ClientDto entityToDto(ClientEntity clientEntity);
    ClientEntity dtoToEntity(ClientDto clientDto);
    List<ClientDto> entityListToDtoList(List<ClientEntity> clientEntities);
}
