package com.mito.neo4j.repository.impl;

import com.mito.neo4j.domain.model.Node;
import com.mito.neo4j.domain.repository.NodeRepository;
import com.mito.neo4j.domain.model.GraphNode;
import com.mito.neo4j.domain.repository.SpringDataNodeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NodeRepositoryAdapter implements NodeRepository {

    private final SpringDataNodeRepository springDataNodeRepository;
    
    public NodeRepositoryAdapter(SpringDataNodeRepository springDataNodeRepository) {
        this.springDataNodeRepository = springDataNodeRepository;
    }

    @Override
    public Node createNode(Node node) {
        GraphNode graphNode = toGraphNode(node);
        GraphNode savedNode = springDataNodeRepository.save(graphNode);
        return toNode(savedNode);
    }

    @Override
    public Optional<Node> findNodeById(String id) {
        return springDataNodeRepository.findById(id)
                .map(this::toNode);
    }

    @Override
    public List<Node> findNodesByLabel(String label) {
        return springDataNodeRepository.findByLabel(label).stream()
                .map(this::toNode)
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> findAllNodes() {
        return springDataNodeRepository.findAll().stream()
                .map(this::toNode)
                .collect(Collectors.toList());
    }

    @Override
    public Node updateNode(Node node) {
        Optional<GraphNode> existingNodeOpt = springDataNodeRepository.findById(node.getId());
        
        if (existingNodeOpt.isPresent()) {
            GraphNode existingNode = existingNodeOpt.get();
            existingNode.setProperties(node.getProperties());
            GraphNode updatedNode = springDataNodeRepository.save(existingNode);
            return toNode(updatedNode);
        }
        
        // Se il nodo non esiste, crealo
        return createNode(node);
    }

    @Override
    public boolean deleteNode(String id) {
        if (springDataNodeRepository.existsById(id)) {
            springDataNodeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private GraphNode toGraphNode(Node node) {
        return GraphNode.of(node.getId(), node.getLabel(), node.getProperties());
    }
    
    private Node toNode(GraphNode graphNode) {
        return Node.of(graphNode.getId(), graphNode.getLabel(), graphNode.getProperties());
    }
}