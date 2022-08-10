package com.baeldung.jackson.node;

import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeIterator {
    
    private static final String NEW_LINE = "\n";
    private static final String FIELD_DELIMITER = ": ";
    private static final String ARRAY_PREFIX = "- ";
    private static final String YAML_PREFIX = "  ";
    
    public String toYaml(JsonNode root) {
        StringBuilder yaml = new StringBuilder();
        processNode(root, yaml, 0);
        return yaml.toString();
    }

    private void processNode(JsonNode jsonNode, StringBuilder yaml, int depth) {
        if (jsonNode.isValueNode()) {
            yaml.append(jsonNode.asText());
        }
        else if (jsonNode.isArray()) {
            for (JsonNode arrayItem : jsonNode) {
                appendNodeToYaml(arrayItem, yaml, depth, true);
            }
        }
        else if (jsonNode.isObject()) {
            appendNodeToYaml(jsonNode, yaml, depth, false);
        }
    }
    
    private void appendNodeToYaml(JsonNode node, StringBuilder yaml, int depth, boolean isArrayItem) {
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        boolean isFirst = true;
        while (fields.hasNext()) {
            Entry<String, JsonNode> jsonField = fields.next();
            addFieldNameToYaml(yaml, jsonField.getKey(), depth, isArrayItem && isFirst);
            processNode(jsonField.getValue(), yaml, depth+1);
            isFirst = false;
        }
        
    }

    private void addFieldNameToYaml(StringBuilder yaml, String fieldName, int depth, boolean isFirstInArray) {
        if (yaml.length()>0) {
            yaml.append(NEW_LINE);
            int requiredDepth = (isFirstInArray) ? depth-1 : depth;
            for(int i = 0; i < requiredDepth; i++) {
                yaml.append(YAML_PREFIX);
            }
            if (isFirstInArray) {
                yaml.append(ARRAY_PREFIX);
            }
        }
        yaml.append(fieldName);
        yaml.append(FIELD_DELIMITER);
    }

}
