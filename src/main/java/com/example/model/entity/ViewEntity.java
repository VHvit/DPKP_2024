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
@Table(name = "view")

public class ViewEntity {

    @Id
    @Column(name = "view_id")
    private UUID viewId = UUID.randomUUID();
    private String value;
}
