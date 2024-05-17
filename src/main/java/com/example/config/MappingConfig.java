package com.example.config;

import com.example.mapping.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {

    @Bean
    public ContractStatusMapping contractStatusMapping() {
        return new ContractStatusMappingImpl();
    }

    @Bean
    public CityMapping cityMapping() {
        return new CityMappingImpl();
    }

    @Bean
    public TypeMapping typeMapping() {
        return new TypeMappingImpl();
    }

    @Bean
    public StatusMapping statusMapping() {
        return new StatusMappingImpl();
    }

    @Bean
    public ViewMapping viewsMapping() {
        return new ViewMappingImpl();
    }

    @Bean
    public BranchMapping branchMapping() {
        return new BranchMappingImpl();
    }

    @Bean
    public CompanyMapping companyMapping() {
        return new CompanyMappingImpl();
    }

    @Bean
    public ContractMapping contractMapping() {
        return new ContractMappingImpl();
    }

    @Bean
    public ClientMapping clientMapping() {
        return new ClientMappingImpl();
    }

    @Bean
    public UserMapping userMapping() {
        return new UserMappingImpl();
    }

    @Bean
    public ContractCreateMapping contractCreateMapping() {
        return new ContractCreateMappingImpl();
    }

    @Bean
    public ContractUpdateMapping contractUpdateMapping() {
        return new ContractUpdateMappingImpl();
    }
}

