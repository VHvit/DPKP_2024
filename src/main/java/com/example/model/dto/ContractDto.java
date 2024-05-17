package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class ContractDto {
    private UUID contractId;
    private String  summ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private ClientDto  client;
    private ViewDto  view;
    private BranchDto branch;
    private UserDto user;
    private ContractStatusDto contractStatus;
}
