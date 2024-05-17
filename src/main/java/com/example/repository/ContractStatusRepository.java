package com.example.repository;

import com.example.model.entity.ContractStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractStatusRepository extends JpaRepository<ContractStatusEntity, UUID> {
    List<ContractStatusEntity> findByValueContaining(String value);
}
