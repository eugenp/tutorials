package com.baeldung.kubernetes.intro;

import io.kubernetes.client.openapi.ApiCallback;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import okhttp3.Call;

@FunctionalInterface
public interface ApiInvoker<R> {
    Call apply(CoreV1Api api, ApiCallback<R> callback) throws ApiException;
}
