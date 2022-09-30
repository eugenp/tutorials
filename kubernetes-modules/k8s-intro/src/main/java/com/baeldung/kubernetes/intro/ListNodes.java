/**
 * 
 */
package com.baeldung.kubernetes.intro;


import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;

/**
 * @author Philippe
 *
 */
public class ListNodes {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        
        ApiClient client  = Config.defaultClient();
        CoreV1Api api = new CoreV1Api(client);
        V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, 10, false);
        nodeList.getItems()
          .stream()
          .forEach((node) -> System.out.println(node.getMetadata()));
    }

}
