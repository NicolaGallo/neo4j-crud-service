package com.mito.neo4j.api.dto;

import javax.validation.constraints.NotBlank;

public class CreateRelationshipRequest {
    @NotBlank(message = "Relationship type is required")
    private String type;

    @NotBlank(message = "Source node ID is required")
    private Long sourceNodeId;

    @NotBlank(message = "Target node ID is required")
    private Long targetNodeId;

    public CreateRelationshipRequest() {
    }

    public CreateRelationshipRequest(String type, Long sourceNodeId, Long targetNodeId) {
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
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
