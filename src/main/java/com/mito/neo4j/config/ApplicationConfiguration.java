package com.mito.neo4j.config;

import com.mito.neo4j.repository.impl.Neo4jGraphOperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public CommandLineRunner initDatabase(Neo4jGraphOperationRepository graphOperationRepository) {
        return args -> {
            // Initialize the operations table
            graphOperationRepository.initTable();
        };
    }
}
