/**
 * 
 */
package com.baeldung.kubernetes.intro;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1JobBuilder;
import io.kubernetes.client.openapi.models.V1JobSpec;
import io.kubernetes.client.openapi.models.V1JobSpecBuilder;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.util.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Philippe
 *
 */
public class RunJob {
    
    private static Logger log = LoggerFactory.getLogger(RunJob.class);
    
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
        BatchV1Api api = new BatchV1Api(client);
        String ns = "report-jobs";
        V1Job body = new V1JobBuilder()
          .withNewMetadata()
            .withNamespace(ns)
            .withName("payroll-report-job")
            .and()
          .withNewSpec()
            .withNewTemplate()
              .withNewMetadata()
                .addToLabels("name", "payroll-report")
                .and()
              .editOrNewSpec()
                .addNewContainer()
                  .withName("main")
                  .withImage("alpine")
                  .addNewCommand("/bin/sh")
                  .addNewArg("-c")
                  .addNewArg("sleep 10")
                  .and()
                .withRestartPolicy("Never")
                .and()
              .and()
            .and()
          .build();
        
        V1Job createdJob = api.createNamespacedJob(ns, body, null, null, null);
        log.info("job: uid={}", createdJob.getMetadata().getUid());

        while(!jobCompleted(api,createdJob)) {
            log.info("[I75] still running...");
            Thread.sleep(1000);
        }
    }

    private static boolean jobCompleted(BatchV1Api api, V1Job createdJob) throws Exception {
        
        V1Job job = api.readNamespacedJob(
            createdJob.getMetadata().getName(), 
            createdJob.getMetadata().getNamespace(), 
            null,null,null);

        if ( job.getStatus() == null ) {
            return false;
        }
        
        log.info("[I88] Status: active={}, succeeded={}, failed={}", 
            job.getStatus().getActive(),
            job.getStatus().getSucceeded(),
            job.getStatus().getFailed()
            );
        Integer active = job.getStatus().getActive();
        
        return active == null || active == 0 ;
    }

}
