package com.mito.neo4j.repository.impl;

import com.mito.neo4j.domain.model.Relationship;
import com.mito.neo4j.domain.repository.RelationshipRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.MapAccessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class Neo4jRelationshipRepository implements RelationshipRepository {

    private final Driver driver;

    public Neo4jRelationshipRepository(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Relationship createRelationship(Relationship relationship) {
        try (Session session = driver.session()) {
            String query = "MATCH (source {id: $sourceId}), (target {id: $targetId}) " +
                           "CREATE (source)-[r:" + relationship.getType() + " {id: $id}]->(target) " +
                           "RETURN r, source.id as sourceId, target.id as targetId";
            
            Record record = session.writeTransaction(tx -> {
                Result result = tx.run(query, 
                    Values.parameters(
                        "id", relationship.getId(),
                        "sourceId", relationship.getSourceNodeId(),
                        "targetId", relationship.getTargetNodeId()
                    ));
                return result.single();
            });
            
            return mapToRelationship(record);
        }
    }

    @Override
    public Optional<Relationship> findRelationshipById(String id) {
        try (Session session = driver.session()) {
            String query = "MATCH (source)-[r {id: $id}]->(target) " +
                           "RETURN r, type(r) as type, source.id as sourceId, target.id as targetId";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("id", id));
                if (result.hasNext()) {
                    Record record = result.single();
                    return Optional.of(mapToRelationship(record));
                }
                return Optional.empty();
            });
        }
    }

    @Override
    public List<Relationship> findRelationshipsByType(String type) {
        try (Session session = driver.session()) {
            String query = "MATCH (source)-[r:" + type + "]->(target) " +
                           "RETURN r, type(r) as type, source.id as sourceId, target.id as targetId";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                List<Relationship> relationships = new ArrayList<>();
                
                while (result.hasNext()) {
                    Record record = result.next();
                    relationships.add(mapToRelationship(record));
                }
                
                return relationships;
            });
        }
    }

    @Override
    public List<Relationship> findRelationshipsBySourceNodeId(String nodeId) {
        try (Session session = driver.session()) {
            String query = "MATCH (source {id: $nodeId})-[r]->(target) " +
                           "RETURN r, type(r) as type, source.id as sourceId, target.id as targetId";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("nodeId", nodeId));
                List<Relationship> relationships = new ArrayList<>();
                
                while (result.hasNext()) {
                    Record record = result.next();
                    relationships.add(mapToRelationship(record));
                }
                
                return relationships;
            });
        }
    }

    @Override
    public List<Relationship> findRelationshipsByTargetNodeId(String nodeId) {
        try (Session session = driver.session()) {
            String query = "MATCH (source)-[r]->(target {id: $nodeId}) " +
                           "RETURN r, type(r) as type, source.id as sourceId, target.id as targetId";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("nodeId", nodeId));
                List<Relationship> relationships = new ArrayList<>();
                
                while (result.hasNext()) {
                    Record record = result.next();
                    relationships.add(mapToRelationship(record));
                }
                
                return relationships;
            });
        }
    }

    @Override
    public boolean deleteRelationship(String id) {
        try (Session session = driver.session()) {
            String query = "MATCH ()-[r {id: $id}]->() DELETE r RETURN count(r) as count";
            
            Integer count = session.writeTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("id", id));
                return result.single().get("count").asInt();
            });
            
            return count > 0;
        }
    }

    private Relationship mapToRelationship(Record record) {
        Value relationshipValue = record.get("r");
        String type = record.get("type").asString();
        String sourceId = record.get("sourceId").asString();
        String targetId = record.get("targetId").asString();
        
        MapAccessor relProps = relationshipValue.asRelationship();
        String id = relProps.get("id").asString();
        
        return Relationship.of(id, type, sourceId, targetId);
    }
}