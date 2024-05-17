package com.example.repository;

import com.example.model.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {
    List<StatusEntity> findByValueContaining(String value);
}
