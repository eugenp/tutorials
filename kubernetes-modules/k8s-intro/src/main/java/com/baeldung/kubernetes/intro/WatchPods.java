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
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import io.kubernetes.client.util.Watch.Response;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class WatchPods {

    private static Logger log = LoggerFactory.getLogger(WatchPods.class);

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

        // Create the watch object that monitors pod creation/deletion/update events
        while (true) {
            log.info("[I46] Creating watch...");
            try (Watch<V1Pod> watch = Watch.createWatch(
              client,
              api.listPodForAllNamespacesCall(false, null, null, null, null, "false", null, null, 10, true, null),
              new TypeToken<Response<V1Pod>>(){}.getType())) {

                log.info("[I52] Receiving events:");
                for (Response<V1Pod> event : watch) {
                    V1Pod pod = event.object;
                    V1ObjectMeta meta = pod.getMetadata();
                    switch (event.type) {
                    case "ADDED":
                    case "MODIFIED":
                    case "DELETED":
                        log.info("event.type: {}, namespace={}, name={}",
                          event.type,
                          meta.getNamespace(),
                          meta.getName());
                        break;
                    default:
                        log.warn("[W66] Unknown event type: {}", event.type);
                    }
                }
            } catch (ApiException ex) {
                log.error("[E70] ApiError", ex);
            }
        }
    }
}
