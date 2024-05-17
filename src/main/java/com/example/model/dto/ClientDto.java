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

public class ClientDto {
    private UUID clientId;
    private String firstname;
    private String lastname;
    private String middlename;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String phone;
    private StatusDto status;
}
