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

    private final Long id;
    private final OperationType type;
    private final Long entityId;
    private final LocalDateTime timestamp;

    private GraphOperation(Long id, OperationType type, Long entityId, LocalDateTime timestamp) {
        this.id = id;
        this.type = type;
        this.entityId = entityId;
        this.timestamp = timestamp;
    }

    public static GraphOperation of(OperationType type, Long entityId) {
        long id = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        return new GraphOperation(
                id,
                type,
                entityId,
                LocalDateTime.now()
        );
    }

    public Long getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public Long getEntityId() {
        return entityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
