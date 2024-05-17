package com.example.model.entity;

//import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Data
@Entity
//@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city")

public class CityEntity {

    @Id
    @Column(name = "city_id")
    private UUID cityId = UUID.randomUUID();
    private String value;
}
