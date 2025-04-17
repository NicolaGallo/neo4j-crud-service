package com.mito.neo4j.domain.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.DynamicLabels;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Collections;

@Node
public class GraphNode {
    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    
    @DynamicLabels
    private final java.util.Set<String> labels = new java.util.LinkedHashSet<>();
    
    private Map<String, Object> properties = new HashMap<>();

    // Costruttore di default richiesto da Spring Data
    public GraphNode() {
    }

    private GraphNode(String id, String label, Map<String, Object> properties) {
        this.id = id;
        if (label != null) {
            this.labels.add(label);
        }
        
        if (properties != null) {
            this.properties = new HashMap<>(properties);
        }
    }

    public static GraphNode create(String label, Map<String, Object> properties) {
        return new GraphNode(null, label, properties);
    }

    public static GraphNode of(String id, String label, Map<String, Object> properties) {
        return new GraphNode(id, label, properties);
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return labels.isEmpty() ? "" : labels.iterator().next();
    }
    
    public void setLabel(String label) {
        this.labels.clear();
        if (label != null) {
            this.labels.add(label);
        }
    }

    public Map<String, Object> getProperties() {
        return Collections.unmodifiableMap(properties);
    }
    
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties != null ? new HashMap<>(properties) : new HashMap<>();
    }

    public GraphNode updateProperties(Map<String, Object> newProperties) {
        GraphNode updatedNode = new GraphNode(this.id, this.getLabel(), this.properties);
        if (newProperties != null) {
            updatedNode.properties.putAll(newProperties);
        }
        return updatedNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphNode node = (GraphNode) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}