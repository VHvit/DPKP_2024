package com.example.repository;

import com.example.model.entity.ViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ViewRepository extends JpaRepository<ViewEntity, UUID> {
    List<ViewEntity> findByValueContaining(String value);
}
