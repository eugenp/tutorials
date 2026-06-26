package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.util.IOUtil;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.CN;
import java.io.IOException;

/**
 * File-backed repository for persisted user settings.
 */
public class SettingsRepository {
    private static final String FILE_NAME = "settings.json";

    private final ActivityJsonCodec codec = new ActivityJsonCodec();

    /**
     * Loads settings from local storage.
     *
     * @return loaded settings, or app defaults when no file exists
     * @throws IOException if the file cannot be read
     */
    public AppSettings load() throws IOException {
        String path = filePath();
        if (!FileSystemStorage.getInstance().exists(path)) {
            return AppSettings.defaults();
        }
        String json = IOUtil.readUtf8File(path);
        if (json == null || json.trim().length() == 0) {
            return AppSettings.defaults();
        }
        return codec.decodeSettings(json);
    }

    /**
     * Persists the current settings.
     *
     * @param settings settings to persist
     */
    public void save(AppSettings settings) {
        try {
            IOUtil.writeUtf8File(filePath(), codec.encodeSettings(settings));
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save settings", ex);
        }
    }

    /**
     * Deletes the persisted settings file.
     */
    public void reset() {
        String path = filePath();
        if (FileSystemStorage.getInstance().exists(path)) {
            FileSystemStorage.getInstance().delete(path);
        }
    }

    /**
     * Returns the storage file path used by the repository.
     * <p>
     * Tests can redirect the repository through the {@code dailyroutine.appHome} system property.
     *
     * @return full settings file path
     */
    protected String filePath() {
        String override = System.getProperty("dailyroutine.appHome");
        if (override != null && override.trim().length() > 0) {
            return (override.endsWith("/") ? override : override + "/") + FILE_NAME;
        }
        return CN.getAppHomePath() + FILE_NAME;
    }
}
