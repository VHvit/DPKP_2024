package com.example.service;

import com.example.mapping.ViewMapping;
import com.example.model.dto.ViewDto;
import com.example.model.entity.ViewEntity;
import com.example.repository.ViewRepository;
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
public class ViewService {

    private final ViewRepository viewRepository;
    private final ViewMapping viewMapping;
    private final JdbcTemplate jdbcTemplate;


    public ViewDto save(ViewDto viewDto) {
        ViewEntity viewEntity = viewMapping.dtoToEntity(viewDto);
        return viewMapping.entityToDto(
                viewRepository.save(viewEntity)
        );
    }

    public ViewDto createView(ViewDto viewDto) {
        ViewEntity viewEntity = viewMapping.dtoToEntity(viewDto);
        viewEntity.setViewId(UUID.randomUUID());
        ViewEntity savedViewEntity = viewRepository.save(viewEntity);
        return viewMapping.entityToDto(savedViewEntity);
    }

    public List<ViewDto> getAllView() {
        List<ViewEntity> viewEntities = viewRepository.findAll();
        return viewEntities.stream()
                .map(viewMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public ViewDto updateView(UUID id, ViewDto viewDto) {
        ViewEntity existingViewEntity = viewRepository.findById(id).orElse(null);
        if (existingViewEntity != null) {
            existingViewEntity.setValue(viewDto.getValue());
            ViewEntity updatedViewEntity = viewRepository.save(existingViewEntity);
            return viewMapping.entityToDto(updatedViewEntity);
        }
        return null;
    }

    public void deleteView(UUID viewId) {
        viewRepository.deleteById(viewId);
    }

    public long viewCount() {
        return viewRepository.count();
    }

    public int getTotalRecordCount(UUID viewId) {
        String sql = "SELECT COUNT(*) AS total_count FROM ( " +
                "    SELECT view_id FROM \"insurance\".\"view\" WHERE view_id = ? " +
                "    UNION ALL " +
                "    SELECT view_id FROM \"insurance\".\"contract\" WHERE view_id = ? " +
                ") AS counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{viewId, viewId}, Integer.class);
    }

    public List<ViewDto> searchView(String query) {
        List<ViewEntity> viewEntities = viewRepository.findByValueContaining(query);
        return viewMapping.entityListToDtoList(viewEntities);
    }

}