package com.mito.neo4j.domain.repository;

import com.mito.neo4j.domain.model.Node;

import java.util.List;
import java.util.Optional;

public interface NodeRepository {
    /**
     * Creates a new node in the graph
     * @param node The node to create
     * @return The created node with generated ID
     */
    Node createNode(Node node);

    /**
     * Retrieves a node by its ID
     * @param id The ID of the node to retrieve
     * @return An Optional containing the node if found, or empty otherwise
     */
    Optional<Node> findNodeById(String id);

    /**
     * Retrieves all nodes with a specific label
     * @param label The label to filter nodes by
     * @return A list of nodes with the specified label
     */
    List<Node> findNodesByLabel(String label);

    /**
     * Retrieves all nodes in the database
     * @return A list of all nodes
     */
    List<Node> findAllNodes();

    /**
     * Updates the properties of an existing node
     * @param node The node with updated properties
     * @return The updated node
     */
    Node updateNode(Node node);

    /**
     * Deletes a node by its ID
     * @param id The ID of the node to delete
     * @return true if the node was deleted, false otherwise
     */
    boolean deleteNode(String id);
}