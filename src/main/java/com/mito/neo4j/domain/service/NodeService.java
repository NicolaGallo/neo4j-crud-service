package com.mito.neo4j.domain.service;

import com.mito.neo4j.domain.model.GraphOperation;
import com.mito.neo4j.domain.model.Node;
import com.mito.neo4j.domain.repository.GraphOperationRepository;
import com.mito.neo4j.domain.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NodeService {

    private final NodeRepository nodeRepository;
    private final GraphOperationRepository operationRepository;

    public NodeService(NodeRepository nodeRepository, GraphOperationRepository operationRepository) {
        this.nodeRepository = nodeRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Node createNode(String label, Map<String, Object> properties) {
        Node node = Node.create(label, properties);
        Node createdNode = nodeRepository.createNode(node);
        
        operationRepository.recordOperation(
            GraphOperation.of(GraphOperation.OperationType.CREATE_NODE, createdNode.getId())
        );
        
        return createdNode;
    }

    public Optional<Node> findNodeById(String id) {
        return nodeRepository.findNodeById(id);
    }

    public List<Node> findNodesByLabel(String label) {
        return nodeRepository.findNodesByLabel(label);
    }
    
    public List<Node> findAllNodes() {
        return nodeRepository.findAllNodes();
    }

    @Transactional
    public Optional<Node> updateNode(String id, Map<String, Object> properties) {
        return nodeRepository.findNodeById(id)
            .map(existingNode -> {
                Node updatedNode = existingNode.updateProperties(properties);
                Node result = nodeRepository.updateNode(updatedNode);
                
                operationRepository.recordOperation(
                    GraphOperation.of(GraphOperation.OperationType.UPDATE_NODE, result.getId())
                );
                
                return result;
            });
    }

    @Transactional
    public boolean deleteNode(String id) {
        boolean deleted = nodeRepository.deleteNode(id);
        
        if (deleted) {
            operationRepository.recordOperation(
                GraphOperation.of(GraphOperation.OperationType.DELETE_NODE, id)
            );
        }
        
        return deleted;
    }
}