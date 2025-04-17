package com.mito.neo4j.api.controller;

import com.mito.neo4j.api.dto.ApiResponse;
import com.mito.neo4j.api.dto.CreateNodeRequest;
import com.mito.neo4j.api.dto.NodeDTO;
import com.mito.neo4j.api.dto.UpdateNodeRequest;
import com.mito.neo4j.domain.model.Node;
import com.mito.neo4j.domain.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);
    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NodeDTO>> createNode(@Valid @RequestBody CreateNodeRequest request) {
        logger.info("Creating node with label: {}", request.getLabel());
        
        Node createdNode = nodeService.createNode(request.getLabel(), request.getProperties());
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Node created successfully", NodeDTO.fromDomain(createdNode)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NodeDTO>> getNodeById(@PathVariable Long id) {
        logger.info("Fetching node with ID: {}", id);
        
        return nodeService.findNodeById(id)
                .map(node -> ResponseEntity.ok(ApiResponse.success(NodeDTO.fromDomain(node))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Node not found with ID: " + id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NodeDTO>>> getNodes(@RequestParam(required = false) String label) {
        if (label != null && !label.isEmpty()) {
            logger.info("Fetching nodes with label: {}", label);
            List<Node> nodes = nodeService.findNodesByLabel(label);
            List<NodeDTO> nodeDTOs = nodes.stream()
                    .map(NodeDTO::fromDomain)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success(nodeDTOs));
        } else {
            logger.info("Fetching all nodes");
            List<Node> nodes = nodeService.findAllNodes();
            List<NodeDTO> nodeDTOs = nodes.stream()
                    .map(NodeDTO::fromDomain)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(ApiResponse.success(nodeDTOs));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NodeDTO>> updateNode(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNodeRequest request) {
        logger.info("Updating node with ID: {}", id);
        
        return nodeService.updateNode(id, request.getProperties())
                .map(updatedNode -> ResponseEntity.ok(
                        ApiResponse.success("Node updated successfully", NodeDTO.fromDomain(updatedNode))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Node not found with ID: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNode(@PathVariable Long id) {
        logger.info("Deleting node with ID: {}", id);
        
        boolean deleted = nodeService.deleteNode(id);
        
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Node deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Node not found with ID: " + id));
        }
    }
}