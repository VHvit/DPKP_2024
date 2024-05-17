package com.example.service;

import com.example.mapping.CompanyMapping;
import com.example.model.dto.CompanyDto;
import com.example.model.entity.CityEntity;
import com.example.model.entity.CompanyEntity;
import com.example.model.entity.TypeEntity;
import com.example.model.search.SearchCompanyDto;
import com.example.repository.CityRepository;
import com.example.repository.CompanyRepository;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final TypeRepository typeRepository;
    private final CityRepository cityRepository;
    private final CompanyMapping companyMapping;
    private final JdbcTemplate jdbcTemplate;


    public CompanyDto save(CompanyDto companyDto) {
        CompanyEntity companyEntity = companyMapping.dtoToEntity(companyDto);
        return companyMapping.entityToDto(
                companyRepository.save(companyEntity)
        );
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        CompanyEntity companyEntity = companyMapping.dtoToEntity(companyDto);
        companyEntity.setCompanyId(UUID.randomUUID());
        CompanyEntity savedCompanyEntity = companyRepository.save(companyEntity);
        return companyMapping.entityToDto(savedCompanyEntity);
    }

    public List<CompanyDto> getAllCompany() {
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        return companyEntities.stream()
                .map(companyMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public CompanyDto updateCompany(UUID id, CompanyDto companyDto) {
        CompanyEntity existingCompanyEntity = companyRepository.findById(id).orElse(null);
        if (existingCompanyEntity != null) {

            existingCompanyEntity.setCompanyName(companyDto.getCompanyName());
            existingCompanyEntity.setLicenseYear(companyDto.getLicenseYear());
            existingCompanyEntity.setPhone(companyDto.getPhone());

            TypeEntity typeEntity = typeRepository.findById(companyDto.getType().getTypeId()).orElse(null);
            existingCompanyEntity.setType(typeEntity);

            CityEntity cityEntity = cityRepository.findById(companyDto.getCity().getCityId()).orElse(null);
            existingCompanyEntity.setCity(cityEntity);

            CompanyEntity updatedCompanyEntity = companyRepository.save(existingCompanyEntity);
            return companyMapping.entityToDto(updatedCompanyEntity);
        }
        return null;
    }

    public void deleteCompany(UUID companyId) {
        companyRepository.deleteById(companyId);
    }

    public long companyCount() {
        return companyRepository.count();
    }


    public int getTotalRecordCount(UUID companyId) {
        String sql = "SELECT COUNT(*) AS total_count " +
                "FROM (" +
                "    SELECT company_id FROM \"insurance\".\"company\" WHERE company_id = ? " +
                "    UNION ALL " +
                "    SELECT company_id FROM \"insurance\".\"branch\" WHERE company_id = ? " +
                "    UNION ALL " +
                "    SELECT branch.company_id FROM \"insurance\".\"contract\" INNER JOIN \"insurance\".\"branch\" ON \"insurance\".\"contract\".branch_id = \"insurance\".\"branch\".branch_id WHERE \"insurance\".\"branch\".company_id = ? " +
                ") AS combined_data";

        return jdbcTemplate.queryForObject(sql, new Object[]{companyId, companyId, companyId}, Integer.class);
    }

    public SearchCompanyDto getCompany(String companyName, UUID cityId) {
        Specification<CompanyEntity> spec = Specification.where(null);

        if (companyName != null) {
            spec = spec.and(hasCompanyName(companyName));
        }
        if (cityId != null) {
            spec = spec.and(hasCityId(cityId));
        }

        List<CompanyEntity> companies = companyRepository.findAll(spec);

        List<CompanyDto> companyDtos = companies.stream()
                .map(companyMapping::entityToDto)
                .collect(Collectors.toList());

        SearchCompanyDto searchCompanyDto = new SearchCompanyDto();
        searchCompanyDto.setNumberOfCompany(companyDtos.size());
        searchCompanyDto.setCompany(companyDtos);

        return searchCompanyDto;
    }

    public static Specification<CompanyEntity> hasCompanyName(String companyName) {
        return companyName == null ? null : (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("companyName"), companyName);
    }

    public static Specification<CompanyEntity> hasCityId(UUID cityId) {
        return cityId == null ? null : (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city").get("cityId"), cityId);
    }

}
