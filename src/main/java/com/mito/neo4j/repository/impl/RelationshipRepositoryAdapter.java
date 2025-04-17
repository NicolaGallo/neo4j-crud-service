package com.mito.neo4j.repository.impl;

import com.mito.neo4j.domain.model.Relationship;
import com.mito.neo4j.domain.repository.RelationshipRepository;
import com.mito.neo4j.domain.model.GraphRelationship;
import com.mito.neo4j.domain.repository.SpringDataRelationshipRepository;
import com.mito.neo4j.domain.repository.SpringDataNodeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RelationshipRepositoryAdapter implements RelationshipRepository {

    private final SpringDataRelationshipRepository springDataRelationshipRepository;
    private final SpringDataNodeRepository springDataNodeRepository;
    
    public RelationshipRepositoryAdapter(
            SpringDataRelationshipRepository springDataRelationshipRepository,
            SpringDataNodeRepository springDataNodeRepository) {
        this.springDataRelationshipRepository = springDataRelationshipRepository;
        this.springDataNodeRepository = springDataNodeRepository;
    }

    @Override
    public Relationship createRelationship(Relationship relationship) {
        GraphRelationship graphRelationship = toGraphRelationship(relationship);
        
        // Assicurati che target node sia impostato
        springDataNodeRepository.findById(relationship.getTargetNodeId())
            .ifPresent(graphRelationship::setTargetNode);
        
        GraphRelationship savedRelationship = springDataRelationshipRepository.save(graphRelationship);
        return toRelationship(savedRelationship);
    }

    @Override
    public Optional<Relationship> findRelationshipById(String id) {
        return springDataRelationshipRepository.findById(id)
                .map(this::toRelationship);
    }

    @Override
    public List<Relationship> findRelationshipsByType(String type) {
        return springDataRelationshipRepository.findByType(type).stream()
                .map(this::toRelationship)
                .collect(Collectors.toList());
    }

    @Override
    public List<Relationship> findRelationshipsBySourceNodeId(String nodeId) {
        return springDataRelationshipRepository.findBySourceNodeId(nodeId).stream()
                .map(this::toRelationship)
                .collect(Collectors.toList());
    }

    @Override
    public List<Relationship> findRelationshipsByTargetNodeId(String nodeId) {
        return springDataRelationshipRepository.findByTargetNodeId(nodeId).stream()
                .map(this::toRelationship)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteRelationship(String id) {
        if (springDataRelationshipRepository.existsById(id)) {
            springDataRelationshipRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private GraphRelationship toGraphRelationship(Relationship relationship) {
        return GraphRelationship.of(
            relationship.getId(), 
            relationship.getType(), 
            relationship.getSourceNodeId(), 
            relationship.getTargetNodeId()
        );
    }
    
    private Relationship toRelationship(GraphRelationship graphRelationship) {
        return Relationship.of(
            graphRelationship.getId(),
            graphRelationship.getType(),
            graphRelationship.getSourceNodeId(),
            graphRelationship.getTargetNodeId()
        );
    }
}