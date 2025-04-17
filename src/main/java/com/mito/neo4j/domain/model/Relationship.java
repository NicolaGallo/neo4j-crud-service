package com.mito.neo4j.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Relationship {
    private final String id;
    private final String type;
    private final String sourceNodeId;
    private final String targetNodeId;

    private Relationship(String id, String type, String sourceNodeId, String targetNodeId) {
        this.id = id;
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public static Relationship create(String type, String sourceNodeId, String targetNodeId) {
        return new Relationship(UUID.randomUUID().toString(), type, sourceNodeId, targetNodeId);
    }

    public static Relationship of(String id, String type, String sourceNodeId, String targetNodeId) {
        return new Relationship(id, type, sourceNodeId, targetNodeId);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public String getTargetNodeId() {
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
