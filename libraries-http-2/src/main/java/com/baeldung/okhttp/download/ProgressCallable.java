package com.baeldung.okhttp.download;

public interface ProgressCallable {

    void onProgress(double progress);

}
