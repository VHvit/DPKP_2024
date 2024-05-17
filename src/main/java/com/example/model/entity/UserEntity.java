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
@Table(name = "user")

public class UserEntity {

    @Id
    private String  login;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;
}
