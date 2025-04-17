package com.mito.neo4j.domain.repository;

import com.mito.neo4j.domain.model.Relationship;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository {
    /**
     * Creates a new relationship between two nodes
     * @param relationship The relationship to create
     * @return The created relationship with generated ID
     */
    Relationship createRelationship(Relationship relationship);

    /**
     * Retrieves a relationship by its ID
     * @param id The ID of the relationship to retrieve
     * @return An Optional containing the relationship if found, or empty otherwise
     */
    Optional<Relationship> findRelationshipById(String id);

    /**
     * Retrieves all relationships of a specific type
     * @param type The type of relationships to retrieve
     * @return A list of relationships of the specified type
     */
    List<Relationship> findRelationshipsByType(String type);

    /**
     * Retrieves all relationships where the specified node is the source
     * @param nodeId The ID of the source node
     * @return A list of relationships originating from the specified node
     */
    List<Relationship> findRelationshipsBySourceNodeId(String nodeId);

    /**
     * Retrieves all relationships where the specified node is the target
     * @param nodeId The ID of the target node
     * @return A list of relationships targeting the specified node
     */
    List<Relationship> findRelationshipsByTargetNodeId(String nodeId);

    /**
     * Deletes a relationship by its ID
     * @param id The ID of the relationship to delete
     * @return true if the relationship was deleted, false otherwise
     */
    boolean deleteRelationship(String id);
}
