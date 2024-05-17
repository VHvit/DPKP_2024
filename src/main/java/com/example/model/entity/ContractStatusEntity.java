package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract_status")

public class ContractStatusEntity {

    @Id
    @Column(name = "contract_status_id")
    private UUID contractStatusId = UUID.randomUUID();
    private String value;
}
