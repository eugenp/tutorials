package com.baeldung.okhttp.download;

public interface ProgressCallback {

    void onProgress(double progress);

}
