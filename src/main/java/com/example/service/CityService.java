package com.example.service;

import com.example.mapping.CityMapping;
import com.example.model.dto.CityDto;
import com.example.model.entity.CityEntity;
import com.example.repository.CityRepository;
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
//@RequestScope
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapping cityMapping;
    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    @Qualifier("dataSourcePerUser")
//    private DataSource dataSourcePerUser;
//
//    @PersistenceUnit
//    private final EntityManagerFactory entityManagerFactory;
//
//    @PostConstruct
//    public void init() {
//        reconfigureEntityManager();
//    }
//
//    private void reconfigureEntityManager() {
//        Properties properties = new Properties();
//        properties.put(Environment.DATASOURCE, dataSourcePerUser);
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(properties)
//                .build();
//
//        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
////        sessionFactory.close();
//        sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
//    }

    public CityDto save(CityDto cityDto) {
        CityEntity cityEntity = cityMapping.dtoToEntity(cityDto);
        return cityMapping.entityToDto(
                cityRepository.save(cityEntity)
        );
    }

    public CityDto createCity(CityDto cityDto) {
        CityEntity cityEntity = cityMapping.dtoToEntity(cityDto);
        cityEntity.setCityId(UUID.randomUUID());
        CityEntity savedCityEntity = cityRepository.save(cityEntity);
        return cityMapping.entityToDto(savedCityEntity);
    }

    public List<CityDto> getAllCity() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        return cityEntities.stream()
                .map(cityMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public CityDto updateCity(UUID id, CityDto cityDto) {
        CityEntity existingCityEntity = cityRepository.findById(id).orElse(null);
        if (existingCityEntity != null) {
            existingCityEntity.setValue(cityDto.getValue());
            CityEntity updatedCityEntity = cityRepository.save(existingCityEntity);
            return cityMapping.entityToDto(updatedCityEntity);
        }
        return null;
    }

    public void deleteCity(UUID cityId) {
        cityRepository.deleteById(cityId);
    }

    public long cityCount() {
        return cityRepository.count();
    }


    public int getTotalRecordCount(UUID cityId) {
        String sql = "SELECT SUM(total_count) total_records FROM (" +
                "SELECT COUNT(*) total_count FROM \"insurance\".\"city\" WHERE city_id = ? " +
                "UNION ALL " +
                "SELECT COUNT(*) FROM \"insurance\".\"company\" WHERE city_id = ? " +
                "UNION ALL " +
                "SELECT COUNT(*) FROM \"insurance\".\"branch\" WHERE city_id = ? " +
                "UNION ALL " +
                "SELECT COUNT(*) FROM \"insurance\".\"contract\" " +
                "INNER JOIN \"insurance\".\"branch\" ON \"insurance\".\"contract\".branch_id = \"insurance\".\"branch\".branch_id " +
                "WHERE \"insurance\".\"branch\".city_id = ? " +
                ") total_counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{cityId, cityId, cityId, cityId}, Integer.class);
    }

    public int select1() {
        String sql = "SELECT 1";

        return jdbcTemplate.queryForObject(sql, new Object[]{}, Integer.class);
    }

    public List<CityDto> searchCities(String query) {
        List<CityEntity> cities = cityRepository.findByValueContaining(query);
        return cityMapping.entityListToDtoList(cities);
    }

}
