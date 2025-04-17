package com.mito.neo4j.api.dto;

import javax.validation.constraints.NotBlank;

public class CreateRelationshipRequest {
    @NotBlank(message = "Relationship type is required")
    private String type;

    @NotBlank(message = "Source node ID is required")
    private String sourceNodeId;

    @NotBlank(message = "Target node ID is required")
    private String targetNodeId;

    public CreateRelationshipRequest() {
    }

    public CreateRelationshipRequest(String type, String sourceNodeId, String targetNodeId) {
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
