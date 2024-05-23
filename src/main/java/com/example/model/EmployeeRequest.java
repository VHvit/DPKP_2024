package com.example.model;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String username;
    private String password;
    private String branchId;
}
