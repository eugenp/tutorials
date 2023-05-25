package com.baeldung.kubernetes.intro;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.kubernetes.client.openapi.ApiCallback;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import okhttp3.Call;

public class AsyncHelper<R>  implements ApiCallback<R> {
    
    private static final Logger log = LoggerFactory.getLogger(AsyncHelper.class);
    
    private CoreV1Api api;
    private CompletableFuture<R> callResult;
    
    private AsyncHelper(CoreV1Api api) {
        this.api = api;
    }
    
    public static <T> CompletableFuture<T> doAsync(CoreV1Api api, ApiInvoker<T> invoker) {
        
        AsyncHelper<T> p = new AsyncHelper<>(api);
        return p.execute(invoker);
    }
    
    private CompletableFuture<R> execute( ApiInvoker<R> invoker) {
        
        try {
            callResult = new CompletableFuture<>();
            log.info("[I38] Calling API...");
            final Call call = invoker.apply(api,this);
            log.info("[I41] API Succesfully invoked: method={}, url={}",
              call.request().method(),
              call.request().url());
            return callResult;
        }
        catch(ApiException aex) {
            callResult.completeExceptionally(aex);
            return callResult;
        }
    }

    @Override
    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
        log.error("[E53] onFailure",e);
        callResult.completeExceptionally(e);
    }

    @Override
    public void onSuccess(R result, int statusCode, Map<String, List<String>> responseHeaders) {
        log.error("[E61] onSuccess: statusCode={}",statusCode);
        callResult.complete(result);
    }

    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
        log.info("[E61] onUploadProgress: bytesWritten={}, contentLength={}, done={}",bytesWritten,contentLength,done);
    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
        log.info("[E75] onDownloadProgress: bytesRead={}, contentLength={}, done={}",bytesRead,contentLength,done);
    }
}

