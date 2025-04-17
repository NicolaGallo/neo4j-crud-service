package com.mito.neo4j.domain.service;

import com.mito.neo4j.domain.model.GraphOperation;
import com.mito.neo4j.domain.model.Relationship;
import com.mito.neo4j.domain.repository.GraphOperationRepository;
import com.mito.neo4j.domain.repository.NodeRepository;
import com.mito.neo4j.domain.repository.RelationshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final NodeRepository nodeRepository;
    private final GraphOperationRepository operationRepository;

    public RelationshipService(
        RelationshipRepository relationshipRepository,
        NodeRepository nodeRepository,
        GraphOperationRepository operationRepository
    ) {
        this.relationshipRepository = relationshipRepository;
        this.nodeRepository = nodeRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Optional<Relationship> createRelationship(String type, String sourceNodeId, String targetNodeId) {
        // Verify that both nodes exist
        boolean sourceExists = nodeRepository.findNodeById(sourceNodeId).isPresent();
        boolean targetExists = nodeRepository.findNodeById(targetNodeId).isPresent();
        
        if (!sourceExists || !targetExists) {
            return Optional.empty();
        }
        
        Relationship relationship = Relationship.create(type, sourceNodeId, targetNodeId);
        Relationship createdRelationship = relationshipRepository.createRelationship(relationship);
        
        operationRepository.recordOperation(
            GraphOperation.of(GraphOperation.OperationType.CREATE_RELATIONSHIP, createdRelationship.getId())
        );
        
        return Optional.of(createdRelationship);
    }

    public Optional<Relationship> findRelationshipById(String id) {
        return relationshipRepository.findRelationshipById(id);
    }

    public List<Relationship> findRelationshipsByType(String type) {
        return relationshipRepository.findRelationshipsByType(type);
    }

    public List<Relationship> findRelationshipsBySourceNodeId(String nodeId) {
        return relationshipRepository.findRelationshipsBySourceNodeId(nodeId);
    }

    public List<Relationship> findRelationshipsByTargetNodeId(String nodeId) {
        return relationshipRepository.findRelationshipsByTargetNodeId(nodeId);
    }

    @Transactional
    public boolean deleteRelationship(String id) {
        boolean deleted = relationshipRepository.deleteRelationship(id);
        
        if (deleted) {
            operationRepository.recordOperation(
                GraphOperation.of(GraphOperation.OperationType.DELETE_RELATIONSHIP, id)
            );
        }
        
        return deleted;
    }
}
