package com.mito.neo4j.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class GraphOperation {
    public enum OperationType {
        CREATE_NODE,
        UPDATE_NODE,
        DELETE_NODE,
        CREATE_RELATIONSHIP,
        DELETE_RELATIONSHIP
    }

    private final String id;
    private final OperationType type;
    private final String entityId;
    private final LocalDateTime timestamp;

    private GraphOperation(String id, OperationType type, String entityId, LocalDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }

    public static GraphOperation of(OperationType type, String entityId) {
        return new GraphOperation(
                UUID.randomUUID().toString(),
                type,
                entityId,
                LocalDateTime.now()
        );
    }

    public String getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public String getEntityId() {
        return entityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
