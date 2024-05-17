package com.example.service;

import com.example.mapping.StatusMapping;
import com.example.model.dto.StatusDto;
import com.example.model.dto.TypeDto;
import com.example.model.entity.StatusEntity;
import com.example.model.entity.TypeEntity;
import com.example.repository.StatusRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapping statusMapping;
    private final JdbcTemplate jdbcTemplate;



    public StatusDto save(StatusDto statusDto) {
        StatusEntity statusEntity = statusMapping.dtoToEntity(statusDto);
        return statusMapping.entityToDto(
                statusRepository.save(statusEntity)
        );
    }

    public StatusDto createStatus(StatusDto statusDto) {
        StatusEntity statusEntity = statusMapping.dtoToEntity(statusDto);
        statusEntity.setStatusId(UUID.randomUUID());
        StatusEntity savedStatusEntity = statusRepository.save(statusEntity);
        return statusMapping.entityToDto(savedStatusEntity);
    }

    public List<StatusDto> getAllStatus() {
        List<StatusEntity> statusEntities = statusRepository.findAll();
        return statusEntities.stream()
                .map(statusMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public StatusDto updateStatus(UUID id, StatusDto statusDto) {
        StatusEntity existingStatusEntity = statusRepository.findById(id).orElse(null);
        if (existingStatusEntity != null) {
            existingStatusEntity.setValue(statusDto.getValue());
            StatusEntity updatedStatusEntity = statusRepository.save(existingStatusEntity);
            return statusMapping.entityToDto(updatedStatusEntity);
        }
        return null;
    }

    public void deleteStatus(UUID statusId) {
        statusRepository.deleteById(statusId);
    }

    public long statusCount() {
        return statusRepository.count();
    }

    public int getTotalRecordCount(UUID cityId) {
        String sql = "SELECT SUM(total_count) AS total_records FROM (" +
                "    SELECT COUNT(*) AS total_count FROM \"insurance\".\"status\" WHERE status_id = ? " +
                "    UNION ALL " +
                "    SELECT COUNT(*) FROM \"insurance\".\"client\" WHERE status_id = ? " +
                "    UNION ALL " +
                "    SELECT COUNT(*) FROM \"insurance\".\"contract\" " +
                "    INNER JOIN \"insurance\".\"client\" ON \"insurance\".\"contract\".client_id = \"insurance\".\"client\".client_id WHERE \"insurance\".\"client\".status_id = ? " +
                ") AS total_counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{cityId, cityId, cityId}, Integer.class);
    }

    public List<StatusDto> searchStatus(String query) {
        List<StatusEntity> statusEntities = statusRepository.findByValueContaining(query);
        return statusMapping.entityListToDtoList(statusEntities);
    }

}