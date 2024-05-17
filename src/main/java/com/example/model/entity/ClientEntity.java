package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")

public class ClientEntity {

    @Id
    @Column(name = "client_id")
    private UUID clientId = UUID.randomUUID();
    private String firstname;
    private String lastname;
    private String middlename;
    @Column(columnDefinition = "date")
    private Date birthday;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;
}
