package com.baeldung.kubernetes.intro;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class WatchPodsUsingBookmarks {

    private static Logger log = LoggerFactory.getLogger(WatchPodsUsingBookmarks.class);

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
            // Get a fresh list only we need to resync
            if ( resourceVersion == null ) {
                log.info("[I48] Creating initial POD list...");
                V1PodList podList = api.listPodForAllNamespaces(true, null, null, null, null, "false", resourceVersion, null, null, null);
                resourceVersion = podList.getMetadata().getResourceVersion();
            }

            while (true) {
                log.info("[I54] Creating watch: resourceVersion={}", resourceVersion);
                try (Watch<V1Pod> watch = Watch.createWatch(
                  client,
                  api.listPodForAllNamespacesCall(true, null, null, null, null, "false", resourceVersion, null, 10, true, null),
                  new TypeToken<Response<V1Pod>>(){}.getType())) {
                    
                    log.info("[I60] Receiving events:");
                    for (Response<V1Pod> event : watch) {
                        V1Pod pod = event.object;
                        V1ObjectMeta meta = pod.getMetadata();
                        switch (event.type) {
                        case "BOOKMARK":
                            resourceVersion = meta.getResourceVersion();
                            log.info("[I67] event.type: {}, resourceVersion={}", event.type,resourceVersion);
                            break;
                        case "ADDED":
                        case "MODIFIED":
                        case "DELETED":
                            log.info("event.type: {}, namespace={}, name={}",
                              event.type,
                              meta.getNamespace(),
                              meta.getName());
                            break;
                        default:
                            log.warn("[W76] Unknown event type: {}", event.type);
                        }
                    }
                } catch (ApiException ex) {
                    log.error("[E80] ApiError", ex);
                    resourceVersion = null;
                }
            }
        }
    }
}
