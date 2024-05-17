package com.example.mapping;

import com.example.model.dto.BranchDto;
import com.example.model.entity.BranchEntity;
import org.mapstruct.Mapper;

import org.springframework.stereotype.Component;

@Mapper
public interface BranchMapping {

    BranchDto entityToDto(BranchEntity branchEntity);
    BranchEntity dtoToEntity(BranchDto branchDto);
}
