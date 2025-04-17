package com.mito.neo4j.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Relationship {
    private final Long id;
    private final String type;
    private final Long sourceNodeId;
    private final Long targetNodeId;

    private Relationship(Long id, String type, Long sourceNodeId, Long targetNodeId) {
        this.id = id;
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public static Relationship create(String type, Long sourceNodeId, Long targetNodeId) {
        long id = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        return new Relationship(id, type, sourceNodeId, targetNodeId);
    }

    public static Relationship of(Long id, String type, Long sourceNodeId, Long targetNodeId) {
        return new Relationship(id, type, sourceNodeId, targetNodeId);
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Long getSourceNodeId() {
        return sourceNodeId;
    }

    public Long getTargetNodeId() {
        return targetNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
