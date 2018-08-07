package com.baeldung.intellij.plugin.stackoverflow.util;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.ide.browsers.WebBrowserUrlProvider;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StackOverflowUrlProvider extends WebBrowserUrlProvider {
    public StackOverflowUrlProvider() {
        super();
    }

    @Override
    public boolean canHandleElement(OpenInBrowserRequest request) {
        return true;
    }

    @Nullable
    @Override
    protected Url getUrl(OpenInBrowserRequest request, VirtualFile file) throws BrowserException {
        return super.getUrl(request, file);
    }

    @NotNull
    @Override
    public Collection<Url> getUrls(OpenInBrowserRequest request) throws BrowserException {
        return super.getUrls(request);
    }
}
