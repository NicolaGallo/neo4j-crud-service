package com.mito.neo4j.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mito.neo4j.domain.model.Relationship;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationshipDTO {
    private Long id;
    private String type;
    private Long sourceNodeId;
    private Long targetNodeId;

    public RelationshipDTO() {
    }

    public RelationshipDTO(Long id, String type, Long sourceNodeId, Long targetNodeId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSourceNodeId() {
        return sourceNodeId;
    }

    public void setSourceNodeId(Long sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    public Long getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(Long targetNodeId) {
        this.targetNodeId = targetNodeId;
    }
}
