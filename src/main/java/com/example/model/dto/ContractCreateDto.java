package com.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class ContractCreateDto {
    private UUID contractId;
    private String  summ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private UUID clientId;
    private ViewDto  view;
    private ContractStatusDto contractStatus;
}
