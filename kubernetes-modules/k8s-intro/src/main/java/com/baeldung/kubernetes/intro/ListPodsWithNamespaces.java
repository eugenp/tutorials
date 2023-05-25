/**
 * 
 */
package com.baeldung.kubernetes.intro;


import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Philippe
 *
 */
public class ListPodsWithNamespaces {
    
    private static final Logger log = LoggerFactory.getLogger(ListPodsWithNamespaces.class);

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        
        ApiClient client  = Config.defaultClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> log.info(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient newClient = client.getHttpClient()
          .newBuilder()
          .addInterceptor(interceptor)
          .readTimeout(0, TimeUnit.SECONDS)
          .build();
        
        client.setHttpClient(newClient);
        CoreV1Api api = new CoreV1Api(client);
        String namespace = "ns1";
        V1PodList items = api.listNamespacedPod(namespace,null, null, null, null, null, null, null, null, 10, false);
        items.getItems()
          .stream()
          .map((pod) -> pod.getMetadata().getName() )
          .forEach((name) -> System.out.println("name=" + name));
        
    }
}
