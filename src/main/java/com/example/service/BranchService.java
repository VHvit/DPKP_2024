package com.example.service;

import com.example.mapping.BranchMapping;
import com.example.model.dto.BranchDto;
import com.example.model.entity.BranchEntity;
import com.example.model.entity.CityEntity;
import com.example.model.entity.CompanyEntity;
import com.example.model.search.SearchBranchDto;
import com.example.repository.BranchRepository;
import com.example.repository.CityRepository;
import com.example.repository.CompanyRepository;
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
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final CityRepository cityRepository;
    private final CompanyRepository companyRepository;
    private final BranchMapping branchMapping;
    private final JdbcTemplate jdbcTemplate;


    public BranchDto save(BranchDto branchDto) {
        BranchEntity branchEntity = branchMapping.dtoToEntity(branchDto);
        return branchMapping.entityToDto(
                branchRepository.save(branchEntity)
        );
    }

    public BranchDto createBranch(BranchDto branchDto) {
        BranchEntity branchEntity = branchMapping.dtoToEntity(branchDto);
        branchEntity.setBranchId(UUID.randomUUID());
        BranchEntity savedBranchEntity = branchRepository.save(branchEntity);
        return branchMapping.entityToDto(savedBranchEntity);
    }

    public List<BranchDto> getAllBranch() {
        List<BranchEntity> branchEntities = branchRepository.findAll();
        return branchEntities.stream()
                .map(branchMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public BranchDto updateBranch(UUID id, BranchDto branchDto) {
        BranchEntity existingBranchEntity = branchRepository.findById(id).orElse(null);
        if (existingBranchEntity != null) {

            existingBranchEntity.setBranchName(branchDto.getBranchName());
            existingBranchEntity.setAddress(branchDto.getAddress());
            existingBranchEntity.setBranchPhone(branchDto.getBranchPhone());
            existingBranchEntity.setEmployeesCount(branchDto.getEmployeesCount());

            CompanyEntity companyEntity = companyRepository.findById(branchDto.getCompany().getCompanyId()).orElse(null);
            existingBranchEntity.setCompany(companyEntity);

            CityEntity cityEntity = cityRepository.findById(branchDto.getCity().getCityId()).orElse(null);
            existingBranchEntity.setCity(cityEntity);

            BranchEntity updatedBranchEntity = branchRepository.save(existingBranchEntity);
            return branchMapping.entityToDto(updatedBranchEntity);
        }
        return null;
    }

    public void deleteBranch(UUID branchId) {
        branchRepository.deleteById(branchId);
    }

    public long branchCount() {
        return branchRepository.count();
    }


    public int getTotalRecordCount(UUID branchId) {
        String sql = "SELECT COUNT(*) AS total_count FROM ( " +
                "    SELECT branch_id FROM branch WHERE branch_id = ? " +
                "    UNION ALL " +
                "    SELECT branch_id FROM \"user\" WHERE branch_id = ? " +
                "    UNION ALL " +
                "    SELECT branch_id FROM contract WHERE branch_id = ? " +
                ") AS counts";


        return jdbcTemplate.queryForObject(sql, new Object[]{branchId, branchId, branchId}, Integer.class);
    }

    public SearchBranchDto getBranch(String branchName, UUID cityId, UUID companyId) {
        Specification<BranchEntity> spec = Specification.where(null);

        if (branchName != null) {
            spec = spec.and(hasBranchName(branchName));
        }
        if (cityId != null) {
            spec = spec.and(hasCityId(cityId));
        }
        if (companyId != null) {
            spec = spec.and(hasCompanyId(companyId));
        }

        List<BranchEntity> branch = branchRepository.findAll(spec);

        List<BranchDto> branchDtos = branch.stream()
                .map(branchMapping::entityToDto)
                .collect(Collectors.toList());

        SearchBranchDto searchBranchDto = new SearchBranchDto();
        searchBranchDto.setNumberOfBranch(branchDtos.size());
        searchBranchDto.setBranch(branchDtos);

        return searchBranchDto;
    }

    private Specification<BranchEntity> hasBranchName(String branchName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("branchName"), branchName);
    }

    private Specification<BranchEntity> hasCityId(UUID cityId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city").get("cityId"), cityId);
    }

    private Specification<BranchEntity> hasCompanyId(UUID companyId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("company").get("companyId"), companyId);
    }
}
