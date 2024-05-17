package com.example.service;

import com.example.mapping.ClientMapping;
import com.example.model.dto.ClientDto;
import com.example.model.entity.ClientEntity;
import com.example.model.entity.ContractEntity;
import com.example.model.entity.StatusEntity;
import com.example.model.search.SearchClientDto;
import com.example.model.search.SearchContractDto;
import com.example.repository.ClientRepository;
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
public class ClientService {

    private final ClientRepository clientRepository;
    private final StatusRepository statusRepository;
    private final ClientMapping clientMapping;
    private final JdbcTemplate jdbcTemplate;


    public ClientDto save(ClientDto clientDto) {
        ClientEntity clientEntity = clientMapping.dtoToEntity(clientDto);
        return clientMapping.entityToDto(
                clientRepository.save(clientEntity)
        );
    }

    public ClientDto createClient(ClientDto clientDto) {
        ClientEntity clientEntity = clientMapping.dtoToEntity(clientDto);
        clientEntity.setClientId(UUID.randomUUID());
        ClientEntity savedClientEntity = clientRepository.save(clientEntity);
        return clientMapping.entityToDto(savedClientEntity);
    }

    public List<ClientDto> getAllClient() {
        List<ClientEntity> clientEntities = clientRepository.findAll();
        return clientEntities.stream()
                .map(clientMapping::entityToDto)
                .collect(Collectors.toList());
    }

    public ClientDto updateClient(UUID id, ClientDto clientDto) {
        ClientEntity existingClientEntity = clientRepository.findById(id).orElse(null);
        if (existingClientEntity != null) {
            existingClientEntity.setFirstname(clientDto.getFirstname());
            existingClientEntity.setLastname(clientDto.getLastname());
            existingClientEntity.setMiddlename(clientDto.getMiddlename());
            existingClientEntity.setBirthday(clientDto.getBirthday());
            existingClientEntity.setPhone(clientDto.getPhone());

            StatusEntity statusEntity = statusRepository.findById(clientDto.getStatus().getStatusId()).orElse(null);
            existingClientEntity.setStatus(statusEntity);

            ClientEntity updatedClientEntity = clientRepository.save(existingClientEntity);
            return clientMapping.entityToDto(updatedClientEntity);
        }
        return null;
    }

    public void deleteClient(UUID clientId) {
        clientRepository.deleteById(clientId);
    }

    public long clientCount() {
        return clientRepository.count();
    }

    public int getTotalRecordCount(UUID clientId) {
        String sql = "SELECT COUNT(*) AS total_count FROM ( " +
                "    SELECT client_id FROM \"insurance\".\"client\" WHERE client_id = ? " +
                "    UNION ALL " +
                "    SELECT client_id FROM \"insurance\".\"contract\" WHERE client_id = ? " +
                ") AS counts";

        return jdbcTemplate.queryForObject(sql, new Object[]{clientId, clientId}, Integer.class);
    }

    public SearchClientDto getClients(String firstname, String lastname, String middlename, Date birthday) {
        Specification<ClientEntity> spec = Specification.where(null);

        if (firstname != null) {
            spec = spec.and(hasFirstname(firstname));
        }
        if (lastname != null) {
            spec = spec.and(hasLastname(lastname));
        }
        if (middlename != null) {
            spec = spec.and(hasMiddlename(middlename));
        }
        if (birthday != null) {
            spec = spec.and(hasBirthdayOnOrAfter(birthday));
        }

        List<ClientEntity> clients = clientRepository.findAll(spec);

        List<ClientDto> clientDto = clients.stream()
                .map(clientMapping::entityToDto)
                .collect(Collectors.toList());

        SearchClientDto searchClientDto = new SearchClientDto();
        searchClientDto.setNumberOfClient(clientDto.size());
        searchClientDto.setClient(clientDto);

        return searchClientDto;
    }

    public static Specification<ClientEntity> hasBirthdayOnOrAfter(Date birthday) {
        return (root, query, criteriaBuilder) ->
                birthday == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), birthday);
    }

    public static Specification<ClientEntity> hasFirstname(String firstname) {
        return (root, query, criteriaBuilder) ->
                firstname == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("firstname"), firstname);
    }

    public static Specification<ClientEntity> hasLastname(String lastname) {
        return (root, query, criteriaBuilder) ->
                lastname == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("lastname"), lastname);
    }

    public static Specification<ClientEntity> hasMiddlename(String middlename) {
        return (root, query, criteriaBuilder) ->
                middlename == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("middlename"), middlename);
    }


}