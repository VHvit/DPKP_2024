package com.example.service;

import com.example.mapping.ContractStatusMapping;
import com.example.model.dto.ContractStatusDto;
import com.example.model.entity.ContractStatusEntity;
import com.example.repository.ContractStatusRepository;
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
public class ContractStatusService {

    private final ContractStatusRepository contractStatusRepository;
    private final ContractStatusMapping contractStatusMapping;
    private final JdbcTemplate jdbcTemplate;


    public ContractStatusDto save(ContractStatusDto contractStatusDto) {
        ContractStatusEntity contractStatusEntity = contractStatusMapping.dtoToEntity(contractStatusDto);
        return contractStatusMapping.entityToDto(
                contractStatusRepository.save(contractStatusEntity)
        );
    }

    public ContractStatusDto createContractStatus(ContractStatusDto contractStatusDto) {
        ContractStatusEntity contractStatusEntity = contractStatusMapping.dtoToEntity(contractStatusDto);
        contractStatusEntity.setContractStatusId(UUID.randomUUID());
        ContractStatusEntity savedContractStatusEntity = contractStatusRepository.save(contractStatusEntity);
        return contractStatusMapping.entityToDto(savedContractStatusEntity);
    }

    public List<ContractStatusDto> getAllContractStatus() {
        List<ContractStatusEntity> contractStatusEntities = contractStatusRepository.findAll();
        return contractStatusEntities.stream()
                .map(contractStatusMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public ContractStatusDto updateContractStatus(UUID id, ContractStatusDto contractStatusDto) {
        ContractStatusEntity existingContractStatusEntity = contractStatusRepository.findById(id).orElse(null);
        if (existingContractStatusEntity != null) {
            existingContractStatusEntity.setValue(contractStatusDto.getValue());
            ContractStatusEntity updatedContractStatusEntity = contractStatusRepository.save(existingContractStatusEntity);
            return contractStatusMapping.entityToDto(updatedContractStatusEntity);
        }
        return null;
    }

    public void deleteContractStatus(UUID contractStatusId) {
        contractStatusRepository.deleteById(contractStatusId);
    }

    public long contractStatusCount() {
        return contractStatusRepository.count();
    }


    public int getTotalRecordCount(UUID contractStatusId) {
        String sql = "SELECT COUNT(*) AS total_count FROM ( " +
                "    SELECT contract_status_id FROM \"insurance\".\"contract_status\" WHERE contract_status_id = ? " +
                "    UNION ALL " +
                "    SELECT contract_status_id FROM \"insurance\".\"contract\" WHERE contract_status_id = ? " +
                ") AS counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{contractStatusId, contractStatusId}, Integer.class);
    }

    public List<ContractStatusDto> searchContractStatus(String query) {
        List<ContractStatusEntity> contractStatusEntities = contractStatusRepository.findByValueContaining(query);
        return contractStatusMapping.entityListToDtoList(contractStatusEntities);
    }
}
