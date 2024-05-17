package com.example.service;

import com.example.mapping.TypeMapping;
import com.example.model.dto.TypeDto;
import com.example.model.dto.ViewDto;
import com.example.model.entity.TypeEntity;
import com.example.model.entity.ViewEntity;
import com.example.repository.TypeRepository;
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
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapping typeMapping;
    private final JdbcTemplate jdbcTemplate;


    public TypeDto save(TypeDto typeDto) {
        TypeEntity typeEntity = typeMapping.dtoToEntity(typeDto);
        return typeMapping.entityToDto(
                typeRepository.save(typeEntity)
        );
    }

    public TypeDto createType(TypeDto typeDto) {
        TypeEntity typeEntity = typeMapping.dtoToEntity(typeDto);
        typeEntity.setTypeId(UUID.randomUUID());
        TypeEntity savedTypeEntity = typeRepository.save(typeEntity);
        return typeMapping.entityToDto(savedTypeEntity);
    }

    public List<TypeDto> getAllType() {
        List<TypeEntity> typeEntities = typeRepository.findAll();
        return typeEntities.stream()
                .map(typeMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public TypeDto updateType(UUID id, TypeDto typeDto) {
        TypeEntity existingTypeEntity = typeRepository.findById(id).orElse(null);
        if (existingTypeEntity != null) {
            existingTypeEntity.setValue(typeDto.getValue());
            TypeEntity updatedTypeEntity = typeRepository.save(existingTypeEntity);
            return typeMapping.entityToDto(updatedTypeEntity);
        }
        return null;
    }

    public void deleteType(UUID typeId) {
        typeRepository.deleteById(typeId);
    }

    public long typeCount() {
        return typeRepository.count();
    }

    public int getTotalRecordCount(UUID typeId) {
        String sql = "SELECT COUNT(*) AS total_count FROM (" +
                "SELECT type_id FROM \"insurance\".\"type\" WHERE type_id = ? " +
                "UNION ALL " +
                "SELECT company_id FROM \"insurance\".\"company\" WHERE type_id = ? " +
                "UNION ALL " +
                "SELECT branch_id FROM \"insurance\".\"branch\" WHERE company_id IN (" +
                "   SELECT company_id FROM \"insurance\".\"company\" WHERE type_id = ?" +
                ") " +
                "UNION ALL " +
                "SELECT contract_id FROM \"insurance\".\"contract\" WHERE branch_id IN (" +
                "   SELECT branch_id FROM \"insurance\".\"branch\" WHERE company_id IN (" +
                "       SELECT company_id FROM \"insurance\".\"company\" WHERE type_id = ?" +
                "   )" +
                ") " +
                ") AS counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{typeId, typeId, typeId, typeId}, Integer.class);
    }

    public List<TypeDto> searchType(String query) {
        List<TypeEntity> typeEntities = typeRepository.findByValueContaining(query);
        return typeMapping.entityListToDtoList(typeEntities);
    }

}