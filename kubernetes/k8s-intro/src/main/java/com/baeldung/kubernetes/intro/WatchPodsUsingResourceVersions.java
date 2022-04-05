package com.baeldung.kubernetes.intro;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import io.kubernetes.client.util.Watch.Response;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class WatchPodsUsingResourceVersions {

    private static Logger log = LoggerFactory.getLogger(WatchPodsUsingResourceVersions.class);

    public static void main(String[] args) throws Exception {

        ApiClient client = Config.defaultClient();

        // Optional, put helpful during tests: disable client timeout and enable
        // HTTP wire-level logs
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> log.info(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient newClient = client.getHttpClient()
          .newBuilder()
          .addInterceptor(interceptor)
          .readTimeout(0, TimeUnit.SECONDS)
           .build();
        
        client.setHttpClient(newClient);
        CoreV1Api api = new CoreV1Api(client);

        String resourceVersion = null;
        while (true) {
            try {
                if ( resourceVersion == null ) {
                    V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, null, resourceVersion, null, null, null);
                    resourceVersion = podList.getMetadata().getResourceVersion();
                }
                
                log.info("[I59] Creating watch: resourceVersion={}", resourceVersion);
                try (Watch<V1Pod> watch = Watch.createWatch(
                  client,
                  api.listPodForAllNamespacesCall(null, null, null, null, null, null, resourceVersion, null, 60, true, null),
                  new TypeToken<Response<V1Pod>>(){}.getType())) {
                        
                    log.info("[I65] Receiving events:");
                    for (Response<V1Pod> event : watch) {
                        V1Pod pod = event.object;
                        V1ObjectMeta meta = pod.getMetadata();
                        switch (event.type) {
                        case "ADDED":
                        case "MODIFIED":
                        case "DELETED":
                            log.info("event: type={}, namespace={}, name={}",
                              event.type,
                              meta.getNamespace(),
                              meta.getName());
                            break;
                        default:
                            log.warn("[W76] Unknown event type: {}", event.type);
                        }
                    }
                }
            }
            catch (ApiException ex) {
                if ( ex.getCode() == 504 || ex.getCode() == 410 ) {
                    resourceVersion = extractResourceVersionFromException(ex);
                }
                else {
                    // Reset resource version
                    resourceVersion = null;
                }
            }
        }
    }

    private static String extractResourceVersionFromException(ApiException ex) {
        
        String body = ex.getResponseBody();
        if (body == null) {
            return null;
        }

        Gson gson = new Gson();
        Map<?,?> st = gson.fromJson(body, Map.class);
        Pattern p = Pattern.compile("Timeout: Too large resource version: (\\d+), current: (\\d+)");
        String msg = (String)st.get("message");
        Matcher m = p.matcher(msg);
        if (!m.matches()) {
            return null;
        }
        
        return m.group(2);
    }

}
