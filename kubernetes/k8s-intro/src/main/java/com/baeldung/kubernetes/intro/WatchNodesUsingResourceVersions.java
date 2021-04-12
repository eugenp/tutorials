package com.baeldung.kubernetes.intro;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.FastDateFormat;
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
import io.kubernetes.client.proto.Meta.Status;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import io.kubernetes.client.util.Watch.Response;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class WatchNodesUsingResourceVersions {

    private static Logger log = LoggerFactory.getLogger(WatchNodesUsingResourceVersions.class);

    public static void main(String[] args) throws Exception {

        ApiClient client = Config.defaultClient();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> log.info(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient newClient = client.getHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(0, TimeUnit.SECONDS)
            .build();
        client.setHttpClient(newClient);

        CoreV1Api api = new CoreV1Api(client);

        // Get the initial pod list. The response includes a 'resourceVersion' field that
        // we'll later use to create a watch
        String resourceVersion = "";

        // Create the watch object that monitors pod creation/deletion/update events
        while (true) {
            try {
                V1PodList podList = api.listPodForAllNamespaces(null, null, null, null, null, "false", resourceVersion, null, 10, null);
                resourceVersion = podList.getMetadata().getResourceVersion();
                log.info("[I54] Starting watch: resourceVersion={}", resourceVersion);
                try (Watch<V1Pod> watch = Watch.createWatch(
                  client,
                  api.listPodForAllNamespacesCall(null, null, null, null, null, "false", resourceVersion, null, 10, true, null),
                  new TypeToken<Response<V1Pod>>(){}.getType())) {
                        
                    log.info("[I68] Processing received events");
                    int eventCount = 0;
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
                        eventCount++;
                    }
                    log.info("[I81] {} event(s) processed",eventCount); 
                }
            }
            catch (ApiException ex) {
                if ( ex.getCode() == 504 ) {
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
        Map<String,Object> st = gson.fromJson(body, Map.class);
        Pattern p = Pattern.compile("Timeout: Too large resource version: (\\d+), current: (\\d+)");
        String msg = (String)st.get("message");
        Matcher m = p.matcher(msg);
        if (!m.matches()) {
            return null;
        }
        
        String currentResourceVersion= m.group(2);
        
        return currentResourceVersion;
    }

    private static FastDateFormat timeOnlyFormat = FastDateFormat.getInstance("hh:MM:ss.SSS");

    private static void dumpPod(V1Pod pod) {

        String startTime;
        if (pod.getStatus() != null && pod.getStatus()
            .getStartTime() != null) {
            startTime = timeOnlyFormat.format(pod.getStatus()
                .getStartTime()
                .toDate());
        } else {
            startTime = "N/A";
        }

        String msg = String.format("%36s %4s %16s %16s %13s %12s %20s %20s", pod.getMetadata()
            .getUid(),
            pod.getMetadata()
                .getResourceVersion(),
            pod.getMetadata()
                .getNamespace(),
            pod.getMetadata()
                .getName(),
            pod.getStatus()
                .getHostIP(),
            startTime, pod.getStatus()
                .getMessage(),
            pod.getSpec()
                .getHostname());

        log.info(msg);
    }
}
