package com.mito.neo4j.domain.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.Objects;
import java.util.UUID;

@RelationshipProperties
public class GraphRelationship {

    @Id
    @GeneratedValue
    private Long id;
    
    private String type;
    
    private Long sourceNodeId;
    
    private Long targetNodeId;
    
    @TargetNode
    private GraphNode targetNode;

    // Costruttore di default richiesto da Spring Data
    public GraphRelationship() {
    }

    private GraphRelationship(Long id, String type, Long sourceNodeId, Long targetNodeId) {
        this.id = id;
        this.type = type;
        this.sourceNodeId = sourceNodeId;
        this.targetNodeId = targetNodeId;
    }

    public static GraphRelationship create(String type, Long sourceNodeId, Long targetNodeId) {
        return new GraphRelationship(null, type, sourceNodeId, targetNodeId);
    }

    public static GraphRelationship of(Long id, String type, Long sourceNodeId, Long targetNodeId) {
        return new GraphRelationship(id, type, sourceNodeId, targetNodeId);
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
    
    public GraphNode getTargetNode() {
        return targetNode;
    }
    
    public void setTargetNode(GraphNode targetNode) {
        this.targetNode = targetNode;
        if (targetNode != null) {
            this.targetNodeId = targetNode.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphRelationship that = (GraphRelationship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}