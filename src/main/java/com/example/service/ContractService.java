package com.example.service;

import com.example.mapping.ContractCreateMapping;
import com.example.mapping.ContractMapping;
import com.example.model.dto.ContractCreateDto;
import com.example.model.dto.ContractDto;
import com.example.model.entity.ClientEntity;
import com.example.model.entity.ContractEntity;
import com.example.model.entity.ContractStatusEntity;
import com.example.model.search.SearchContractDto;
import com.example.repository.ContractRepository;
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
import org.springframework.context.annotation.Lazy;
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
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractStatusRepository contractStatusRepository;
    private final ContractMapping contractMapping;
    private final ContractCreateMapping contractCreateMapping;
    private final JdbcTemplate jdbcTemplate;




    public ContractDto save(ContractDto contractDto) {
        ContractEntity contractEntity = contractMapping.dtoToEntity(contractDto);
        return contractMapping.entityToDto(
                contractRepository.save(contractEntity)
        );
    }

    public ContractDto createContract(ContractCreateDto contractCreateDto) {
        ContractEntity contractEntity = contractCreateMapping.dtoToEntity(contractCreateDto);
        contractEntity.setContractId(UUID.randomUUID());
        ContractEntity savedContractEntity = contractRepository.save(contractEntity);
        return contractCreateMapping.entityToDto(savedContractEntity);
    }



    public List<ContractDto> getAllContract() {
        List<ContractEntity> contractEntities = contractRepository.findAll();
        return contractEntities.stream()
                .map(contractMapping::entityToDto)
                .collect(Collectors.toList());
    }


    public ContractDto updateContract(UUID contractId, ContractCreateDto contractCreateDto) {
        ContractEntity existingContractEntity = contractRepository.findById(contractId).orElse(null);
        if (existingContractEntity != null) {

            ContractStatusEntity contractStatusEntity = contractStatusRepository.findById(contractCreateDto.getContractStatus().getContractStatusId()).orElse(null);
            existingContractEntity.setContractStatus(contractStatusEntity);

            ContractEntity updatedContractEntity = contractRepository.save(existingContractEntity);
            return contractMapping.entityToDto(updatedContractEntity);
        }
        return null;
    }

    public void deleteContract(UUID contractId) {
        contractRepository.deleteById(contractId);
    }

    public long contractCount() {
        return contractRepository.count();
    }

    public int getTotalRecordCount(UUID contractId) {
        String sql = "SELECT COUNT(*) AS contract_count FROM \"insurance\".\"contract\" WHERE contract_id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{contractId}, Integer.class);
    }


//    public SearchContractDto getContracts(String summ, Date date, UUID viewId, UUID contractStatusId) {
//        List<ContractEntity> contracts;
//
//        if (summ != null && date != null && viewId != null && contractStatusId != null) {
//            contracts = contractRepository.findBySummAndDateAndViewViewIdAndContractStatusContractStatusId(
//                    summ,
//                    date,
//                    viewId,
//                    contractStatusId);
//        } else {
//            contracts = contractRepository.findAll();
//        }
//
//        List<ContractDto> contractDtos = contracts.stream()
//                .map(contractMapping::entityToDto)
//                .collect(Collectors.toList());
//
//        SearchContractDto searchContractDto = new SearchContractDto();
//        searchContractDto.setNumberOfContract(contractDtos.size());
//        searchContractDto.setContract(contractDtos);
//
//        return searchContractDto;
//    }

    public List<ContractDto> getContractsByClientId(UUID clientId) {
        List<ContractEntity> contracts = contractRepository.findByClientClientId(clientId);

        List<ContractDto> contractDtos = contracts.stream()
                .map(contractMapping::entityToDto)
                .collect(Collectors.toList());
        return contractDtos;
    }

    public SearchContractDto getContracts(String sum, Date date, UUID viewId, UUID contractStatusId) {
        Specification<ContractEntity> spec = Specification.where(null);

        if (sum != null) {
            spec = spec.and(hasSum(sum));
        }
        if (date != null) {
            spec = spec.and(hasDate(date));
        }
        if (viewId != null) {
            spec = spec.and(hasViewId(viewId));
        }
        if (contractStatusId != null) {
            spec = spec.and(hasContractStatusId(contractStatusId));
        }

        List<ContractEntity> contracts = contractRepository.findAll(spec);

        List<ContractDto> contractDtos = contracts.stream()
                .map(contractMapping::entityToDto)
                .collect(Collectors.toList());

        SearchContractDto searchContractDto = new SearchContractDto();
        searchContractDto.setNumberOfContract(contractDtos.size());
        searchContractDto.setContract(contractDtos);

        return searchContractDto;
    }

    private Specification<ContractEntity> hasSum(String sum) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("summ"), sum);
    }

    public static Specification<ContractEntity> hasDate(Date date) {
        return (root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date);
    }

    private Specification<ContractEntity> hasViewId(UUID viewId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("view").get("viewId"), viewId);
    }

    private Specification<ContractEntity> hasContractStatusId(UUID contractStatusId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("contractStatus").get("contractStatusId"), contractStatusId);
    }

}
