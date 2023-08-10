package com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import reactor.core.publisher.Mono;

@Component
public class ScrubResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<ScrubResponseGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(ScrubResponseGatewayFilterFactory.class);
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilterFactory;

    public ScrubResponseGatewayFilterFactory(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilterFactory) {
        super(Config.class);
        this.modifyResponseBodyFilterFactory = modifyResponseBodyFilterFactory;
    }
    
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("fields", "replacement");
    }


    @Override
    public GatewayFilter apply(Config config) {
        
        return modifyResponseBodyFilterFactory
          .apply(c -> c.setRewriteFunction(JsonNode.class, JsonNode.class, new Scrubber(config)));
    }

    public static class Config {
        
        private String fields;
        private String replacement;
        
        
        public String getFields() {
            return fields;
        }
        public void setFields(String fields) {
            this.fields = fields;
        }
        public String getReplacement() {
            return replacement;
        }
        public void setReplacement(String replacement) {
            this.replacement = replacement;
        }
    }
    
    
    public static class Scrubber implements RewriteFunction<JsonNode,JsonNode> {
        private final Pattern fields;
        private final String replacement;
        
        public Scrubber(Config config) {
            this.fields = Pattern.compile(config.getFields());
            this.replacement = config.getReplacement();
        }

        @Override
        public Publisher<JsonNode> apply(ServerWebExchange t, JsonNode u) {
            return Mono.just(scrubRecursively(u));
        }

        private JsonNode scrubRecursively(JsonNode u) {
            if ( !u.isContainerNode()) {
                return u;
            }
            
            if ( u.isObject()) {
                ObjectNode node = (ObjectNode)u;
                node.fields().forEachRemaining((f) -> {
                    if ( fields.matcher(f.getKey()).matches() && f.getValue().isTextual()) {
                        f.setValue(TextNode.valueOf(replacement));
                    }
                    else {
                        f.setValue(scrubRecursively(f.getValue()));
                    }
                });
            }
            else if ( u.isArray()) {
                ArrayNode array = (ArrayNode)u;
                for ( int i = 0 ; i < array.size() ; i++ ) {
                    array.set(i, scrubRecursively(array.get(i)));
                }
            }
            
            return u;
        }
    }
}
