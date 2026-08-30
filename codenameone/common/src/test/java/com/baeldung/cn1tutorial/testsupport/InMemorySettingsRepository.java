package com.baeldung.cn1tutorial.testsupport;

import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.service.SettingsRepository;
import java.io.IOException;

/**
 * In-memory settings repository used by tests.
 */
public class InMemorySettingsRepository extends SettingsRepository {
    private AppSettings stored = AppSettings.defaults();
    private IOException loadException;

    /**
     * @param initialSettings initial settings snapshot
     */
    public InMemorySettingsRepository(AppSettings initialSettings) {
        stored = initialSettings == null ? AppSettings.defaults() : initialSettings.normalized();
    }

    /**
     * Returns the stored settings, or throws the configured exception.
     */
    @Override
    public AppSettings load() throws IOException {
        if (loadException != null) {
            throw loadException;
        }
        return stored;
    }

    /**
     * Replaces the stored settings snapshot.
     */
    @Override
    public void save(AppSettings settings) {
        stored = settings == null ? AppSettings.defaults() : settings.normalized();
    }

    /**
     * @return currently stored settings
     */
    public AppSettings getStored() {
        return stored;
    }

    /**
     * Replaces the stored settings snapshot.
     *
     * @param settings new settings snapshot
     */
    public void setStored(AppSettings settings) {
        stored = settings == null ? AppSettings.defaults() : settings.normalized();
    }

    /**
     * Configures an exception to throw during the next load.
     *
     * @param loadException exception to throw
     */
    public void setLoadException(IOException loadException) {
        this.loadException = loadException;
    }
}
