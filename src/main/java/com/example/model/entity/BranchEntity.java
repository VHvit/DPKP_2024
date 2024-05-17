package com.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")

public class BranchEntity {

    @Id
    @Column(name = "branch_id")
    private UUID branchId = UUID.randomUUID();
    @Column(name = "branch_name")
    private String branchName;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
    private String address;
    @Column(name = "branch_phone")
    private String branchPhone;
    @Column(name = "employees_count")
    private String employeesCount;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
