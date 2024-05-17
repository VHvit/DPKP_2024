package com.example.model;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeRequest {
    private String username;
    private String password;
    private String branchId;
}
