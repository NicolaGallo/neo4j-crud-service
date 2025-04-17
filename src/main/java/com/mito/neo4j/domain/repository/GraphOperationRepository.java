package com.mito.neo4j.domain.repository;

import com.mito.neo4j.domain.model.GraphOperation;

import java.util.List;

public interface GraphOperationRepository {
    /**
     * Records a graph operation
     * @param operation The operation to record
     * @return The recorded operation
     */
    GraphOperation recordOperation(GraphOperation operation);

    /**
     * Retrieves all operations performed on a specific entity
     * @param entityId The ID of the entity
     * @return A list of operations performed on the entity
     */
    List<GraphOperation> findOperationsByEntityId(String entityId);

    /**
     * Retrieves all operations of a specific type
     * @param type The type of operations to retrieve
     * @return A list of operations of the specified type
     */
    List<GraphOperation> findOperationsByType(GraphOperation.OperationType type);
}
