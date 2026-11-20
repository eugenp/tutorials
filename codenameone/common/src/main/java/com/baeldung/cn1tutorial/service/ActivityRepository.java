package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.util.IOUtil;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.CN;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * File-backed repository for persisted activities.
 * <p>
 * Persistence is intentionally tiny: one JSON file in the CN1 app home directory. This keeps the
 * tutorial focused on forms and services rather than on database integration.
 */
public class ActivityRepository {
    private static final String FILE_NAME = "activities.json";

    private final ActivityJsonCodec codec = new ActivityJsonCodec();

    /**
     * Loads all persisted activities from local storage.
     *
     * @return decoded activities, or an empty list when no file exists
     * @throws IOException if the file cannot be read or parsed
     */
    public List<Activity> loadActivities() throws IOException {
        String path = filePath();
        if (!FileSystemStorage.getInstance().exists(path)) {
            return new ArrayList<Activity>();
        }
        String json = IOUtil.readUtf8File(path);
        if (json == null || json.trim().length() == 0) {
            return new ArrayList<Activity>();
        }
        return codec.decodeActivities(json);
    }

    /**
     * Rewrites the full activity file.
     *
     * @param activities activities to persist
     * @throws IOException if writing fails
     */
    public void saveActivities(List<Activity> activities) throws IOException {
        IOUtil.writeUtf8File(filePath(), codec.encodeActivities(activities));
    }

    /**
     * Deletes the persisted activity file.
     */
    public void reset() {
        String path = filePath();
        if (FileSystemStorage.getInstance().exists(path)) {
            FileSystemStorage.getInstance().delete(path);
        }
    }

    /**
     * Returns the full file path used by the repository.
     * <p>
     * Tests can override the app home directory through the {@code dailyroutine.appHome}
     * system property.
     *
     * @return storage file path
     */
    public String filePath() {
        return appHomePath() + FILE_NAME;
    }

    /**
     * Resolves the effective app home directory.
     *
     * @return directory ending with a slash
     */
    private String appHomePath() {
        String override = System.getProperty("dailyroutine.appHome");
        if (override != null && override.trim().length() > 0) {
            return override.endsWith("/") ? override : override + "/";
        }
        return CN.getAppHomePath();
    }
}
