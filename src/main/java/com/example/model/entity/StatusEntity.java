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
@Table(name = "status")

public class StatusEntity {

    @Id
    @Column(name = "status_id")
    private UUID statusId = UUID.randomUUID();
    private String value;
}
