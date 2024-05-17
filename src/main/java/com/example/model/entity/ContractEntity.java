package com.example.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")

public class ContractEntity {

    @Id
    @Column(name = "contract_id")
    private UUID contractId = UUID.randomUUID();
    private String  summ;
    @Column(columnDefinition = "date")
    private Date date;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private ClientEntity client;
    @Column(name = "client_id")
    private UUID clientId;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "view_id")
    private ViewEntity view;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "login")
    private UserEntity user;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "contract_status_id")
    private ContractStatusEntity contractStatus;
}
