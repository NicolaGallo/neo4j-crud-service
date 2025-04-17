package com.mito.neo4j.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mito.neo4j.domain.model.Node;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeDTO {
    private String id;
    private String label;
    private Map<String, Object> properties;

    public NodeDTO() {
    }

    public NodeDTO(String id, String label, Map<String, Object> properties) {
        this.id = id;
        this.label = label;
        this.properties = properties;
    }

    public static NodeDTO fromDomain(Node node) {
        return new NodeDTO(
            node.getId(),
            node.getLabel(),
            node.getProperties()
        );
    }

    public Node toDomain() {
        return Node.of(id, label, properties);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
