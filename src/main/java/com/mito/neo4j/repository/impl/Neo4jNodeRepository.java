package com.mito.neo4j.repository.impl;

import com.mito.neo4j.domain.model.Node;
import com.mito.neo4j.domain.repository.NodeRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.MapAccessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class Neo4jNodeRepository implements NodeRepository {

    private final Driver driver;

    public Neo4jNodeRepository(Driver driver) {
        this.driver = driver;
    }

    @Override
    public Node createNode(Node node) {
        try (Session session = driver.session()) {
            String query = "CREATE (n:" + node.getLabel() + " {id: $id}) SET n += $properties RETURN n";
            
            Map<String, Object> properties = new HashMap<>(node.getProperties());
            properties.put("id", node.getId());
            
            Record record = session.writeTransaction(tx -> {
                Result result = tx.run(query, 
                    Values.parameters("id", node.getId(), "properties", properties));
                return result.single();
            });
            
            return mapToNode(record.get("n"));
        }
    }

    @Override
    public Optional<Node> findNodeById(String id) {
        try (Session session = driver.session()) {
            String query = "MATCH (n) WHERE n.id = $id RETURN n, labels(n) as labels";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("id", id));
                if (result.hasNext()) {
                    Record record = result.single();
                    return Optional.of(mapToNode(record.get("n"), record.get("labels")));
                }
                return Optional.empty();
            });
        }
    }

    @Override
    public List<Node> findNodesByLabel(String label) {
        try (Session session = driver.session()) {
            String query = "MATCH (n:" + label + ") RETURN n";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                List<Node> nodes = new ArrayList<>();
                
                while (result.hasNext()) {
                    Record record = result.next();
                    nodes.add(mapToNode(record.get("n")));
                }
                
                return nodes;
            });
        }
    }
    
    @Override
    public List<Node> findAllNodes() {
        try (Session session = driver.session()) {
            String query = "MATCH (n) RETURN n, labels(n) as labels";
            
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                List<Node> nodes = new ArrayList<>();
                
                while (result.hasNext()) {
                    Record record = result.next();
                    nodes.add(mapToNode(record.get("n"), record.get("labels")));
                }
                
                return nodes;
            });
        }
    }

    @Override
    public Node updateNode(Node node) {
        try (Session session = driver.session()) {
            String query = "MATCH (n {id: $id}) SET n += $properties RETURN n";
            
            Record record = session.writeTransaction(tx -> {
                Result result = tx.run(query, 
                    Values.parameters("id", node.getId(), "properties", node.getProperties()));
                return result.single();
            });
            
            return mapToNode(record.get("n"));
        }
    }

    @Override
    public boolean deleteNode(String id) {
        try (Session session = driver.session()) {
            String query = "MATCH (n {id: $id}) DETACH DELETE n RETURN count(n) as count";
            
            Integer count = session.writeTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("id", id));
                return result.single().get("count").asInt();
            });
            
            return count > 0;
        }
    }

    private Node mapToNode(Value nodeValue) {
        return mapToNode(nodeValue, null);
    }

    private Node mapToNode(Value nodeValue, Value labelsValue) {
        MapAccessor nodeProps = nodeValue.asNode();
        String id = nodeProps.get("id").asString();
        
        String label;
        if (labelsValue != null) {
            List<String> labels = labelsValue.asList(Value::asString);
            label = labels.stream()
                .filter(l -> !l.equals("Node"))
                .findFirst()
                .orElse("Node");
        } else {
            label = nodeValue.asNode().labels().iterator().next();
        }
        
        Map<String, Object> properties = new HashMap<>();
        nodeProps.keys().forEach(key -> {
            if (!key.equals("id")) {
                properties.put(key, nodeProps.get(key).asObject());
            }
        });
        
        return Node.of(id, label, properties);
    }
}