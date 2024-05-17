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
@Table(name = "company")

public class CompanyEntity {

    @Id
    @Column(name = "companyId")
    private UUID companyId = UUID.randomUUID();
    @Column(name = "company_name")
    private String  companyName;
    @Column(name = "license_year")
    private String  licenseYear;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
    private String  phone;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity type;
}