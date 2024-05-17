package com.example.repository;

import com.example.model.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID>, JpaSpecificationExecutor<ContractEntity> {
    List<ContractEntity> findByClientClientId(UUID clientId);
}