package com.baeldung.okhttp;

import okhttp3.RequestBody;
import okhttp3.MediaType;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestWrapper extends RequestBody {

    protected RequestBody delegate;
    protected ProgressListener listener;

    protected CountingSink countingSink;

    public ProgressRequestWrapper(RequestBody delegate, ProgressListener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return delegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        BufferedSink bufferedSink;

        countingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {

            super.write(source, byteCount);

            bytesWritten += byteCount;
            listener.onRequestProgress(bytesWritten, contentLength());
        }

    }

    public interface ProgressListener {

        void onRequestProgress(long bytesWritten, long contentLength);

    }
}
