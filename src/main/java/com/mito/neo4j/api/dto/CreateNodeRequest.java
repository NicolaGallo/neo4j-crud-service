package com.mito.neo4j.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class CreateNodeRequest {
    @NotBlank(message = "Node label is required")
    private String label;

    @NotNull(message = "Node properties are required")
    private Map<String, Object> properties;

    public CreateNodeRequest() {
    }

    public CreateNodeRequest(String label, Map<String, Object> properties) {
        this.label = label;
        this.properties = properties;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
