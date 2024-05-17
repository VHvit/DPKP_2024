package com.example.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "type")

public class TypeEntity {

    @Id
    @Column(name = "type_id")
    private UUID typeId = UUID.randomUUID();

    @Column(nullable = true)
    private String value;
}
