package com.mito.neo4j.domain.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Node {
    private final Long id;
    private final String label;
    private final Map<String, Object> properties;

    private Node(Long id, String label, Map<String, Object> properties) {
        this.id = id;
        this.label = label;
        this.properties = new HashMap<>(properties);
    }

    public static Node create(String label, Map<String, Object> properties) {
        long id = UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
        return new Node(id, label, properties);
    }

    public static Node of(Long id, String label, Map<String, Object> properties) {
        return new Node(id, label, properties);
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Object> getProperties() {
        return new HashMap<>(properties);
    }

    public Node updateProperties(Map<String, Object> newProperties) {
        Map<String, Object> updatedProperties = new HashMap<>(this.properties);
        updatedProperties.putAll(newProperties);
        return new Node(this.id, this.label, updatedProperties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
