package com.mito.neo4j.api.controller;

import com.mito.neo4j.api.dto.ApiResponse;
import com.mito.neo4j.api.dto.CreateRelationshipRequest;
import com.mito.neo4j.api.dto.RelationshipDTO;
import com.mito.neo4j.domain.model.Relationship;
import com.mito.neo4j.domain.service.RelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/relationships")
public class RelationshipController {

    private static final Logger logger = LoggerFactory.getLogger(RelationshipController.class);
    private final RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RelationshipDTO>> createRelationship(
            @Valid @RequestBody CreateRelationshipRequest request) {
        logger.info("Creating relationship of type: {} between nodes: {} and {}", 
                request.getType(), request.getSourceNodeId(), request.getTargetNodeId());
        
        return relationshipService.createRelationship(
                request.getType(), request.getSourceNodeId(), request.getTargetNodeId())
                .map(relationship -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success("Relationship created successfully", 
                                RelationshipDTO.fromDomain(relationship))))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Source or target node not found")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RelationshipDTO>> getRelationshipById(@PathVariable String id) {
        logger.info("Fetching relationship with ID: {}", id);
        
        return relationshipService.findRelationshipById(id)
                .map(relationship -> ResponseEntity.ok(
                        ApiResponse.success(RelationshipDTO.fromDomain(relationship))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Relationship not found with ID: " + id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RelationshipDTO>>> getRelationshipsByType(
            @RequestParam String type) {
        logger.info("Fetching relationships with type: {}", type);
        
        List<Relationship> relationships = relationshipService.findRelationshipsByType(type);
        List<RelationshipDTO> relationshipDTOs = relationships.stream()
                .map(RelationshipDTO::fromDomain)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(relationshipDTOs));
    }

    @GetMapping("/source/{nodeId}")
    public ResponseEntity<ApiResponse<List<RelationshipDTO>>> getRelationshipsBySourceNodeId(
            @PathVariable String nodeId) {
        logger.info("Fetching relationships with source node ID: {}", nodeId);
        
        List<Relationship> relationships = relationshipService.findRelationshipsBySourceNodeId(nodeId);
        List<RelationshipDTO> relationshipDTOs = relationships.stream()
                .map(RelationshipDTO::fromDomain)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(relationshipDTOs));
    }

    @GetMapping("/target/{nodeId}")
    public ResponseEntity<ApiResponse<List<RelationshipDTO>>> getRelationshipsByTargetNodeId(
            @PathVariable String nodeId) {
        logger.info("Fetching relationships with target node ID: {}", nodeId);
        
        List<Relationship> relationships = relationshipService.findRelationshipsByTargetNodeId(nodeId);
        List<RelationshipDTO> relationshipDTOs = relationships.stream()
                .map(RelationshipDTO::fromDomain)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(relationshipDTOs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRelationship(@PathVariable String id) {
        logger.info("Deleting relationship with ID: {}", id);
        
        boolean deleted = relationshipService.deleteRelationship(id);
        
        if (deleted) {
            return ResponseEntity.ok(ApiResponse.success("Relationship deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Relationship not found with ID: " + id));
        }
    }
}
