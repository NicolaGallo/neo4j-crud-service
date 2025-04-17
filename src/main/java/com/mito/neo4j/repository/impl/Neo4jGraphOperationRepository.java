package com.mito.neo4j.repository.impl;

import com.mito.neo4j.domain.model.GraphOperation;
import com.mito.neo4j.domain.repository.GraphOperationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class Neo4jGraphOperationRepository implements GraphOperationRepository {

    private final JdbcTemplate jdbcTemplate;
    
    private static final String INSERT_OPERATION = 
        "INSERT INTO graph_operations (id, type, entity_id, timestamp) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ENTITY = 
        "SELECT id, type, entity_id, timestamp FROM graph_operations WHERE entity_id = ? ORDER BY timestamp DESC";
    private static final String FIND_BY_TYPE = 
        "SELECT id, type, entity_id, timestamp FROM graph_operations WHERE type = ? ORDER BY timestamp DESC";
    
    private final RowMapper<GraphOperation> operationRowMapper = (rs, rowNum) -> {
        return GraphOperation.of(
            GraphOperation.OperationType.valueOf(rs.getString("type")),
            rs.getLong("entity_id")
        );
    };

    public Neo4jGraphOperationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GraphOperation recordOperation(GraphOperation operation) {
        jdbcTemplate.update(
            INSERT_OPERATION,
            operation.getId(),
            operation.getType().name(),
            operation.getEntityId(),
            Timestamp.valueOf(operation.getTimestamp())
        );
        return operation;
    }

    @Override
    public List<GraphOperation> findOperationsByEntityId(Long entityId) {
        return jdbcTemplate.query(FIND_BY_ENTITY, operationRowMapper, entityId);
    }

    @Override
    public List<GraphOperation> findOperationsByType(GraphOperation.OperationType type) {
        return jdbcTemplate.query(FIND_BY_TYPE, operationRowMapper, type.name());
    }
    
    // This method should be called during application startup
    public void initTable() {
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS graph_operations (" +
            "id VARCHAR(36) PRIMARY KEY, " +
            "type VARCHAR(50) NOT NULL, " +
            "entity_id VARCHAR(36) NOT NULL, " +
            "timestamp TIMESTAMP NOT NULL" +
            ")"
        );
    }
}
