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
public class ListPodsWithFieldSelectors {
    
    private static final Logger log = LoggerFactory.getLogger(ListPodsWithFieldSelectors.class);

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
        
        String fs = createSelector(args);
        V1PodList items = api.listPodForAllNamespaces(null, null, fs, null, null, null, null, null, 10, false);
        items.getItems()
          .stream()
          .map((pod) -> pod.getMetadata().getName() )
          .forEach((name) -> System.out.println("name=" + name));
        
    }

    private static String createSelector(String[] args) {

        StringBuilder b = new StringBuilder();
        for( int i =  0 ; i < args.length; i++ ) {
            if( b.length() > 0 ) {
                b.append(',');
            }
            
            b.append(args[i]);
        }
        
        return b.toString();
    }

}
