package com.example.model.search;

import com.example.model.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchClientDto {
    private int numberOfClient;
    private List<ClientDto> client;
}
