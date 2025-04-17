package com.mito.neo4j.repository;

import com.mito.neo4j.domain.model.GraphRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataRelationshipRepository extends Neo4jRepository<GraphRelationship, Long> {
    
    /**
     * Trova relazioni per tipo
     * @param type Il tipo di relazioni da recuperare
     * @return Lista di relazioni del tipo specificato
     */
    @Query("MATCH (source)-[r:`$type`]->(target) RETURN r, source, target")
    List<GraphRelationship> findByType(String type);
    
    /**
     * Trova relazioni dove il nodo specificato è la fonte
     * @param nodeId L'ID del nodo fonte
     * @return Lista di relazioni originate dal nodo specificato
     */
    @Query("MATCH (source {id: $nodeId})-[r]->(target) RETURN r, source, target")
    List<GraphRelationship> findBySourceNodeId(String nodeId);
    
    /**
     * Trova relazioni dove il nodo specificato è il target
     * @param nodeId L'ID del nodo target
     * @return Lista di relazioni destinate al nodo specificato
     */
    @Query("MATCH (source)-[r]->(target {id: $nodeId}) RETURN r, source, target")
    List<GraphRelationship> findByTargetNodeId(String nodeId);
}