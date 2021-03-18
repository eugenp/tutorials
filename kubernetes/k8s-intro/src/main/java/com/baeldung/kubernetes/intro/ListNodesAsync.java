/**
 * 
 */
package com.baeldung.kubernetes.intro;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;

/**
 * @author Philippe
 *
 */
public class ListNodesAsync {
    
    private static Logger log = LoggerFactory.getLogger(ListNodesAsync.class);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        
        // Initial setup
        ApiClient client  = Config.defaultClient();
        CoreV1Api api = new CoreV1Api(client);
        
        // Start async call
        CompletableFuture<V1NodeList> p = AsyncHelper.doAsync(api,(capi,cb) ->
          capi.listNodeAsync(null, null, null, null, null, null, null, null, 10, false, cb)
        );
        
        p.thenAcceptAsync((nodeList) -> {
            log.info("[I40] Processing results...");
            nodeList.getItems()
            .stream()
            .forEach((node) -> System.out.println(node.getMetadata()));
        });
        
        log.info("[I46] Waiting results...");
        p.get(10, TimeUnit.SECONDS);
    }

}
