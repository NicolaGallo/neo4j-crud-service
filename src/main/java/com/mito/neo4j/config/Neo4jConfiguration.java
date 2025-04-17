package com.mito.neo4j.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class Neo4jConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(Neo4jConfiguration.class);

    @Value("${neo4j.uri}")
    private String neo4jUri;

    @Value("${neo4j.username}")
    private String neo4jUsername;

    @Value("${neo4j.password}")
    private String neo4jPassword;

    @Bean
    public Driver neo4jDriver() {
        logger.info("Connecting to Neo4j at: {}", neo4jUri);
        return GraphDatabase.driver(
            neo4jUri,
            AuthTokens.basic(neo4jUsername, neo4jPassword)
        );
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName("graph-operations")
            .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
