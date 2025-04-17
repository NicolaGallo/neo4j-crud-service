package com.mito.neo4j.api.dto;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdateNodeRequest {
    @NotNull(message = "Node properties are required")
    private Map<String, Object> properties;

    public UpdateNodeRequest() {
    }

    public UpdateNodeRequest(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
