package com.baeldung.connectiondetails.configuration;

import com.baeldung.connectiondetails.adapter.VaultAdapter;
import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinConnectionDetails;

public class CustomZipkinConnectionDetails implements ZipkinConnectionDetails {
    @Override
    public String getSpanEndpoint() {
        return VaultAdapter.getSecret("zipkin_span_endpoint");
    }
}
