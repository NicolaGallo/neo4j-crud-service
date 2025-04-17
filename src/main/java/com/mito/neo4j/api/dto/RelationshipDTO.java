package com.mito.neo4j.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mito.neo4j.domain.model.Relationship;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationshipDTO {
    private String id;
    private String type;
    private String sourceNodeId;
    private String targetNodeId;

    public RelationshipDTO() {
    }

    public RelationshipDTO(String id, String type, String sourceNodeId, String targetNodeId) {
        this.id = id;
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public static RelationshipDTO fromDomain(Relationship relationship) {
        return new RelationshipDTO(
            relationship.getId(),
            relationship.getType(),
            relationship.getSourceNodeId(),
            relationship.getTargetNodeId()
        );
    }

    public Relationship toDomain() {
        return Relationship.of(id, type, sourceNodeId, targetNodeId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }
}
