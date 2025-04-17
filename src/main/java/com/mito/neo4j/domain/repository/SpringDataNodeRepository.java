package com.mito.neo4j.domain.repository;


import com.mito.neo4j.domain.model.GraphNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataNodeRepository extends Neo4jRepository<GraphNode, String> {
    
    /**
     * Trova nodi per etichetta
     * @param label L'etichetta per cui cercare i nodi
     * @return Lista di nodi con l'etichetta specificata
     */
    @Query("MATCH (n) WHERE $label IN labels(n) RETURN n")
    List<GraphNode> findByLabel(String label);
    
    /**
     * Trova tutti i nodi
     * @return Lista di tutti i nodi
     */
    @Query("MATCH (n) RETURN n")
    List<GraphNode> findAllNodes();
}